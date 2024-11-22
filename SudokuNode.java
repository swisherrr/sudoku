public class SudokuNode {
    private SudokuBoard board;
    private int currentBox;  // tracks which box we're working on (0-80)

    public SudokuNode(SudokuBoard board, int currentBox) {
        this.board = new SudokuBoard(board);
        this.currentBox = currentBox;
    }

    public SudokuBoard getBoard() {
        return board;
    }

    public int getCurrentBox() {
        return currentBox;
    }
}