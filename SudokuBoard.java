/**
 * Sudoku Board Class
 * - Represents and manages the Sudoku board state and operations
 *
 * Zac Swisher, Sneha Patel, Jordano Liberato
 * CSC 301
 * 12/06/24
 */
public class SudokuBoard {
    private int[][] board;        //2D array representing the Sudoku board
    private boolean[][] isFixed;  // to track fixed "question" boxes
    private int SIZE;             // board size
    private int subBoxSize;       //size of each sub box(sqrt(n))
    private static final int EMPTY = 0;

    //constructor to initialize the sudoku board with a given size
    public SudokuBoard(int SIZE) {
        this.SIZE = SIZE;
        //improved the subBoxSize calculation by setting it to the square root of SIZE, ensuring the sub-boxes conform to Sudoku rules
        this.subBoxSize = (int) Math.sqrt(SIZE); // sub-box size is the square root of the board size
        this.board = new int[SIZE][SIZE];        //initialize the board as a 2D array of integers
        this.isFixed = new boolean[SIZE][SIZE];  //initialize the fixed cells
    }

    // copy constructor
    public SudokuBoard(SudokuBoard other) {
        this.SIZE = other.SIZE;
        this.subBoxSize = other.subBoxSize;
        this.board = new int[SIZE][SIZE];
        this.isFixed = new boolean[SIZE][SIZE];
        //copy values from the other board to this new board
        //improved it to work for any grid size given instead of fixed size 9
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                this.board[i][j] = other.board[i][j];//copies cell values
                this.isFixed[i][j] = other.isFixed[i][j];//copies fixed cells
            }
        }
    }

    // initialize board from an array and mark fixed cells
    public void initializeBoard(int[][] initial) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = initial[i][j];
                //mark the cell as fixed if it's not empty
                if (initial[i][j] != EMPTY) {
                    isFixed[i][j] = true;// this cell has a given value
                }
            }
        }
    }
    public int getSize(){
        return SIZE;
    }
    public int getSubBoxSize(){
        return subBoxSize;
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
    //updated for dynamic size
    // board display
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < SIZE; i++) {
            // insert a separator line between sub-boxes (if applicable)
            if (i % subBoxSize == 0 && i != 0) {
                sb.append("-".repeat(SIZE * 2 + subBoxSize - 1)).append("\n");
            }
            for (int j = 0; j < SIZE; j++) {
                // insert a separator '|' between sub-boxes
                if (j % subBoxSize == 0 && j != 0) {
                    sb.append("| ");
                }
                sb.append(board[i][j] == 0 ? "." : board[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}