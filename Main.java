import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String filename = "./boards/easy.txt";
        try {
            int[][] initialPuzzle = readPuzzleFromFile(filename);

            // initialize the board
            SudokuBoard board = new SudokuBoard();
            board.initializeBoard(initialPuzzle);

            // display initial board
            System.out.println("Initial board:");
            System.out.println(board);

            SudokuSolver solver = new SudokuSolver();
            SudokuBoard solution = solver.solveBFS(board);

            if (solution != null) {
                System.out.println("\nSolution:");
                System.out.println(solution);
            } else {
                System.out.println("\nNo solution exists!");
            }

        } catch (FileNotFoundException e) {
            System.out.println("error reading file: " + e.getMessage());
        }
    }

    private static int[][] readPuzzleFromFile(String filename) throws FileNotFoundException {
        int[][] puzzle = new int[9][9];
        Scanner scanner = new Scanner(new File(filename));

        for (int i = 0; i < 9; i++) {
            String[] line = scanner.nextLine().trim().split(" ");
            for (int j = 0; j < 9; j++) {
                puzzle[i][j] = Integer.parseInt(line[j]);
            }
        }

        scanner.close();
        return puzzle;
    }
}