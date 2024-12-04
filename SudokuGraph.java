import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
public class SudokuGraph {

    private SudokuNode root;
    private int totalNodes;

    public SudokuGraph(SudokuBoard initialBoard) {
        this.root = new SudokuNode(initialBoard, 0, 0);
        this.totalNodes = 1; // counting root
    }

    // BFS traverse method to build the graph and find solution
    public SudokuNode buildGraphBFS() {
        Queue<SudokuNode> queue = new LinkedList<>(); // queue for traversal
        queue.add(root);
        int size = root.getBoard().getSize(); //given size
        int totalCells = size * size;

        while (!queue.isEmpty()) { // continue while theres still nodes left
            SudokuNode current = queue.poll();

            // if all boxes full, solution.
            if (current.getCurrentBox() >= totalCells) {
                return current;
            }

            // fet row and column from current box number
            int row = current.getCurrentBox() / size;
            int col = current.getCurrentBox() % size;

            // if question box, only one path possible
            if (current.getBoard().isFixed(row, col)) {
                // so, create one child with next box
                SudokuNode child = new SudokuNode(
                        current.getBoard(),
                        current.getCurrentBox() + 1, // move to next box
                        current.getLevel() + 1 // increment level
                );
                current.addChild(child); // add to graph
                queue.add(child); // add to queue
                totalNodes++;
                continue; // go to next iteration
            }

            // for non question boxes, try each possible number
            for (int num = 1; num <= size; num++) {//iterate over possible numbers
                if (isValidMove(current.getBoard(), row, col, num)) {
                    // create new board with this number
                    SudokuBoard newBoard = new SudokuBoard(current.getBoard());
                    newBoard.setValue(row, col, num);

                    // create new node and add to graph and queue
                    SudokuNode child = new SudokuNode(
                            newBoard,
                            current.getCurrentBox() + 1,
                            current.getLevel() + 1
                    );
                    current.addChild(child);
                    queue.add(child);
                    totalNodes++;
                }
            }
        }

        return null;  // no solution found
    }

    public SudokuNode solveDLS() {
        // Start DLS from root with limit of 81 (total cells)
        int size = root.getBoard().getSize(); //given size
        int totalCells = size * size;
        return DLS(root, totalCells);
    }

    private SudokuNode DLS(SudokuNode node, int limit) {
        int size = root.getBoard().getSize(); //given size
        int totalCells = size * size;
        // If we've filled all boxes, we've found a solution
        if (node.getCurrentBox() >= totalCells) {
            return node;
        }

        // If we've hit the depth limit
        if (node.getLevel() >= limit) {
            return null;
        }

        // Get current position
        int row = node.getCurrentBox() / size;
        int col = node.getCurrentBox() % size;

        // If it's a fixed/question box, only one path possible
        if (node.getBoard().isFixed(row, col)) {
            SudokuNode child = new SudokuNode(
                    node.getBoard(),
                    node.getCurrentBox() + 1,
                    node.getLevel() + 1
            );
            node.addChild(child);
            totalNodes++;

            // Immediately explore this path
            return DLS(child, limit);
        }

        // Try each possible number in current position
        for (int num = 1; num <= size; num++) {
            if (isValidMove(node.getBoard(), row, col, num)) {
                // Create new board with this number
                SudokuBoard newBoard = new SudokuBoard(node.getBoard());
                newBoard.setValue(row, col, num);

                // Create child node
                SudokuNode child = new SudokuNode(
                        newBoard,
                        node.getCurrentBox() + 1,
                        node.getLevel() + 1
                );
                node.addChild(child);
                totalNodes++;

                // Immediately explore this path
                SudokuNode result = DLS(child, limit);
                if (result != null) {
                    return result;  // Solution found
                }
                // If no solution found, loop continues to try next number
            }
        }

        return null;  // No solution found in this path
    }

    // method to get the solution path once solution is found
    public List<SudokuNode> getSolutionPath(SudokuNode solutionNode) {
        List<SudokuNode> path = new ArrayList<>();
        SudokuNode current = solutionNode;

        while (current != null) { // traverse back until root
            path.add(0, current);  // add to front of list
            current = current.getParent();
        }

        return path;
    }

    // validates moves
    private boolean isValidMove(SudokuBoard board, int row, int col, int num) {
        // check row
        int size = board.getSize();
        int subBoxSize = board.getSubBoxSize();

        for (int x = 0; x < size; x++) {
            if (board.getValue(row, x) == num) return false;
        }

        // check column
        for (int y = 0; y < size; y++) {
            if (board.getValue(y, col) == num) return false;
        }

        // find top left corner of the 3x3 box
        int boxRow = row - row % subBoxSize;
        int boxCol = col - col % subBoxSize;

        // check 3x3 box for conflicts
        for (int i = 0; i < subBoxSize; i++) {
            for (int j = 0; j < subBoxSize; j++) {
                if (board.getValue(boxRow + i, boxCol + j) == num) return false;
            }
        }

        return true; // move is valid
    }

    public int getTotalNodes() {
        return totalNodes;
    }

    public SudokuNode getRoot() {
        return root;
    }
}