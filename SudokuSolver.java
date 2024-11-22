import java.util.Queue;
import java.util.LinkedList;

public class SudokuSolver {
    // Constants for the Sudoku grid
    private static final int SIZE = 9;
    private static final int EMPTY = 0;

    public SudokuBoard solveBFS(SudokuBoard initial) {
        // create a queue to store nodes for BFS traversal
        Queue<SudokuNode> queue = new LinkedList<>();
        queue.add(new SudokuNode(initial, 0));

        // continue processing nodes until queue is empty
        while (!queue.isEmpty()) {
            // get next node from queue
            SudokuNode currentNode = queue.poll();
            // get the board state from current node
            SudokuBoard currentBoard = currentNode.getBoard();
            // get current box (0-80)
            int currentBox = currentNode.getCurrentBox();

            // if processed all boxes: solution
            if (currentBox >= SIZE * SIZE) {
                return currentBoard;
            }

            // convert box number (0-80) to row (0-8) and column (0-8)
            int row = currentBox / SIZE;
            int col = currentBox % SIZE;

            // if question box, skip to next box
            if (currentBoard.isFixed(row, col)) {
                queue.add(new SudokuNode(currentBoard, currentBox + 1));
                continue;
            }

            // try each number (1-9) in current box
            for (int num = 1; num <= SIZE; num++) {
                // check if this number is valid
                if (isValidMove(currentBoard, row, col, num)) {
                    // create a new board state with this number
                    SudokuBoard newBoard = new SudokuBoard(currentBoard);

                    // place the number in the new board
                    newBoard.setValue(row, col, num);

                    // create new node with updated board and next box number
                    queue.add(new SudokuNode(newBoard, currentBox + 1));
                }
            }
        }

        return null; //queue is empty and no solution found
    }

    // checks if placing 'num' at a position is valid
    private boolean isValidMove(SudokuBoard board, int row, int col, int num) {
        // check if number already exists in the same row
        for (int x = 0; x < SIZE; x++) {
            if (board.getValue(row, x) == num) return false;
        }
        // check if number already exists in the same column
        for (int y = 0; y < SIZE; y++) {
            if (board.getValue(y, col) == num) return false;
        }

        // find top-left corner of 3x3 box containing (row,col)
        int boxRow = row - row % 3;
        int boxCol = col - col % 3;

        // check if number already exists in the same 3x3 box
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.getValue(boxRow + i, boxCol + j) == num) return false;
            }
        }

        return true; // all checks pass, placement is valid
    }
}