/**
 *  Node Class
 * - Represents a node in the search tree, containing board state and position
 *
 * Zac Swisher, Sneha Patel, Jordano Liberato
 * CSC 301
 * 12/06/24
 */
import java.util.ArrayList;
import java.util.List;

public class SudokuNode {
    private SudokuBoard board;
    private int currentBox;  // which box we're working on (0-80)
    private int level;       // level in the graph
    private SudokuNode parent;
    private List<SudokuNode> children;

    public SudokuNode(SudokuBoard board, int currentBox, int level) {
        this.board = new SudokuBoard(board);
        this.currentBox = currentBox;
        this.level = level;
        this.children = new ArrayList<>();
    }

    public void addChild(SudokuNode child) {
        children.add(child);
        child.parent = this;
    }

    public SudokuBoard getBoard() { return board; }
    public int getCurrentBox() { return currentBox; }
    public int getLevel() { return level; }
    public List<SudokuNode> getChildren() { return children; }
    public SudokuNode getParent() { return parent; }
}