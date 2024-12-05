import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

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

    // DLS solution
    public SudokuNode solveDLS() {
        int size = root.getBoard().getSize();
        int totalCells = size * size;
        Stack<DLSState> stack = new Stack<>(); // for backtracking
        SudokuBoard currentBoard = new SudokuBoard(root.getBoard());

        stack.push(new DLSState(0, 1)); // start at first cell

        while (!stack.isEmpty()) {
            DLSState state = stack.peek(); // get current state

            if (state.currentBox >= totalCells) { // if board is full
                return new SudokuNode(new SudokuBoard(currentBoard), totalCells, totalCells);
            }

            // calculate position
            int row = state.currentBox / size;
            int col = state.currentBox % size;

            // if question/fixed box
            if (currentBoard.isFixed(row, col)) {
                stack.pop(); // remove current state
                stack.push(new DLSState(state.currentBox + 1, 1)); // move to next cell
                continue;
            }

            // if tried all numbers,
            if (state.currentNum > size) {
                stack.pop(); // backtrack until no states left
                if (!stack.isEmpty()) {
                    DLSState prevState = stack.peek(); // get previous state
                    undoMove(currentBoard, prevState.currentBox); // undo last move
                    prevState.currentNum++; // try next number
                }
                continue;
            }

            // if move is valid, place the number and move to next cell
            if (isValidMove(currentBoard, row, col, state.currentNum)) {
                currentBoard.setValue(row, col, state.currentNum);
                stack.push(new DLSState(state.currentBox + 1, 1));
            } else {
                state.currentNum++; // try next number
            }
        }

        return null; // no solution
    }

    // undos moves for backtracking
    private void undoMove(SudokuBoard board, int box) {
        // get position
        int row = box / board.getSize();
        int col = box % board.getSize();
        // clear cell
        board.setValue(row, col, 0);
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
}
