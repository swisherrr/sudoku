import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.List;
public class Main {
    public static void main(String[] args) {
        String filename = "./boards/easy.txt";
        try {
            // read and initialize puzzle
            int[][] initialPuzzle = readPuzzleFromFile(filename);
            SudokuBoard initialBoard = new SudokuBoard();
            initialBoard.initializeBoard(initialPuzzle);

            System.out.println("Initial Puzzle:");
            System.out.println(initialBoard);

            // create graph and solve
            SudokuGraph graph = new SudokuGraph(initialBoard);
            long startTime = System.currentTimeMillis();

            SudokuNode solution = graph.buildGraphBFS();

            long endTime = System.currentTimeMillis();

            if (solution != null) {
                System.out.println("\nSolution found!");
                System.out.println(solution.getBoard());
                System.out.println("\nStatistics:");
                System.out.println("Total nodes created: " + graph.getTotalNodes());
                System.out.println("Time taken: " + (endTime - startTime) + "ms");

                // print solution path length
                List<SudokuNode> path = graph.getSolutionPath(solution);
                System.out.println("Solution path length: " + path.size());
            } else {
                System.out.println("\nNo solution exists!");
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error reading puzzle file: " + e.getMessage());
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