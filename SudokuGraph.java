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

        while (!queue.isEmpty()) { // continue while theres still nodes left
            SudokuNode current = queue.poll();

            // if all boxes full, solution.
            if (current.getCurrentBox() >= 81) {
                return current;
            }

            // fet row and column from current box number
            int row = current.getCurrentBox() / 9;
            int col = current.getCurrentBox() % 9;

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
            for (int num = 1; num <= 9; num++) {
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
        for (int x = 0; x < 9; x++) {
            if (board.getValue(row, x) == num) return false;
        }

        // check column
        for (int y = 0; y < 9; y++) {
            if (board.getValue(y, col) == num) return false;
        }

        // find top left corner of the 3x3 box
        int boxRow = row - row % 3;
        int boxCol = col - col % 3;

        // check 3x3 box for conflicts
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
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