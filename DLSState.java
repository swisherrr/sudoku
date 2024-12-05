public class DLSState {
    int currentBox; // current cell position
    int currentNum; // current number being tried

    DLSState(int box, int num) {
        this.currentBox = box;
        this.currentNum = num;
    }
}
