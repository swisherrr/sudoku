import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

public class SudokuGraph {
    private SudokuNode root;
    private int totalNodes;
    private List<SudokuNode> solutions = new ArrayList<>();

    public SudokuGraph(SudokuBoard initialBoard) {
        this.root = new SudokuNode(initialBoard, 0, 0);
        this.totalNodes = 1;
    }

    // BFS traverse method to build the graph and find all solutions
    public List<SudokuNode> buildGraphBFS() {
        Queue<SudokuNode> queue = new LinkedList<>(); // queue for traversal
        queue.add(root); // start at root
        // calculate total cells
        int size = root.getBoard().getSize();
        int totalCells = size * size;

        // process each node
        while (!queue.isEmpty()) {
            SudokuNode current = queue.poll();

            // if all boxes full, add to solutions
            if (current.getCurrentBox() >= totalCells) {
                solutions.add(current);
                continue;  // continue searching for more solutions
            }

            // calculate current position
            int row = current.getCurrentBox() / size;
            int col = current.getCurrentBox() % size;

            // if current cell is fixed/question
            if (current.getBoard().isFixed(row, col)) {
                SudokuNode child = new SudokuNode( // child created w/ same number (only option)
                        current.getBoard(),
                        // go to next cell and increment level
                        current.getCurrentBox() + 1,
                        current.getLevel() + 1
                );
                current.addChild(child); // add to graph
                queue.add(child); // add to queue
                totalNodes++;
                continue; // go to next node
            }

            // try all possible numbers in empty cell
            for (int num = 1; num <= size; num++) {
                if (isValidMove(current.getBoard(), row, col, num)) {
                    SudokuBoard newBoard = new SudokuBoard(current.getBoard());
                    newBoard.setValue(row, col, num);

                    SudokuNode child = new SudokuNode( // create new node for this move
                            newBoard,
                            current.getCurrentBox() + 1,
                            current.getLevel() + 1
                    );
                    current.addChild(child); // add to graph
                    queue.add(child); // add to queue
                    totalNodes++;
                }
            }
        }

        return solutions;
    }

    public List<SudokuNode> solveDLS() {
        int size = root.getBoard().getSize();
        int totalCells = size * size;
        DLS(root, totalCells);
        return solutions;
    }

    // recursive DLS
    private void DLS(SudokuNode node, int limit) {
        // calculate total cells
        int size = root.getBoard().getSize();
        int totalCells = size * size;

        // solution found
        if (node.getCurrentBox() >= totalCells) {
            solutions.add(node);
            return;
        }

        // check depth limit
        if (node.getLevel() >= limit) {
            return;
        }

        // calculate current position
        int row = node.getCurrentBox() / size;
        int col = node.getCurrentBox() % size;

        // for fixed/question boxes
        if (node.getBoard().isFixed(row, col)) {
            SudokuNode child = new SudokuNode(
                    node.getBoard(),
                    node.getCurrentBox() + 1,
                    node.getLevel() + 1
            );
            node.addChild(child);
            totalNodes++;
            DLS(child, limit); // continue search with child
            return;
        }

        // try all possible numbers for that cell
        for (int num = 1; num <= size; num++) {
            if (isValidMove(node.getBoard(), row, col, num)) {
                SudokuBoard newBoard = new SudokuBoard(node.getBoard());
                newBoard.setValue(row, col, num);

                SudokuNode child = new SudokuNode(
                        newBoard,
                        node.getCurrentBox() + 1,
                        node.getLevel() + 1
                );
                node.addChild(child);
                totalNodes++;
                DLS(child, limit); // recursively try this path
            }
        }
    }

    public List<SudokuNode> getSolutionPath(SudokuNode solutionNode) {
        List<SudokuNode> path = new ArrayList<>();
        SudokuNode current = solutionNode;

        while (current != null) {
            path.add(0, current);
            current = current.getParent();
        }

        return path;
    }

    private boolean isValidMove(SudokuBoard board, int row, int col, int num) {
        int size = board.getSize();
        int subBoxSize = board.getSubBoxSize();

        // check row
        for (int x = 0; x < size; x++) {
            if (board.getValue(row, x) == num) return false;
        }

        // check column
        for (int y = 0; y < size; y++) {
            if (board.getValue(y, col) == num) return false;
        }

        // calculate first cell in box
        int boxRow = row - row % subBoxSize;
        int boxCol = col - col % subBoxSize;

        // check box
        for (int i = 0; i < subBoxSize; i++) {
            for (int j = 0; j < subBoxSize; j++) {
                if (board.getValue(boxRow + i, boxCol + j) == num) return false;
            }
        }

        return true;
    }

    public void clearSolutions() {
        solutions.clear();
    }

    public int getTotalNodes() {
        return totalNodes;
    }

    public SudokuNode getRoot() {
        return root;
    }
}