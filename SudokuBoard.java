public class SudokuBoard {
    private int[][] board;
    private boolean[][] isFixed;  // to track fixed "question" boxes
    private static final int SIZE = 9;
    private static final int EMPTY = 0;

    public SudokuBoard() {
        board = new int[SIZE][SIZE];
        isFixed = new boolean[SIZE][SIZE];
    }

    // copy constructor
    public SudokuBoard(SudokuBoard other) {
        this.board = new int[SIZE][SIZE];
        this.isFixed = new boolean[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                this.board[i][j] = other.board[i][j];
                this.isFixed[i][j] = other.isFixed[i][j];
            }
        }
    }

    // initialize board from an array and mark fixed cells
    public void initializeBoard(int[][] initial) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = initial[i][j];
                if (initial[i][j] != EMPTY) {
                    isFixed[i][j] = true;
                }
            }
        }
    }

    public boolean isFixed(int row, int col) {
        return isFixed[row][col];
    }

    public int getValue(int row, int col) {
        return board[row][col];
    }

    public void setValue(int row, int col, int value) {
        board[row][col] = value;
    }

    // board display
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < SIZE; i++) {
            if (i % 3 == 0 && i != 0) {
                sb.append("-".repeat(21)).append("\n");
            }
            for (int j = 0; j < SIZE; j++) {
                if (j % 3 == 0 && j != 0) {
                    sb.append("| ");
                }
                sb.append(board[i][j] == 0 ? "." : board[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}