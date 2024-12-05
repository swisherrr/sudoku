/**
 * Sudoku Board Class
 * - Helper class for DLS algorithm to track state during search
 *
 * Zac Swisher, Sneha Patel, Jordano Liberato
 * CSC 301
 * 12/06/24
 */
public class DLSState {
    int currentBox; // current cell position
    int currentNum; // current number being tried

    DLSState(int box, int num) {
        this.currentBox = box;
        this.currentNum = num;
    }
}
