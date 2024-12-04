import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.List;
public class Main {
    public static void main(String[] args) {
        String filename = "./boards/25.txt";
        int size = 25;
        try {
            // read and initialize puzzle
            int[][] initialPuzzle = readPuzzleFromFile(filename,size);
            SudokuBoard initialBoard = new SudokuBoard(size);
            initialBoard.initializeBoard(initialPuzzle);

            System.out.println("Initial Puzzle:");
            System.out.println(initialBoard);

            // create graph and solve
            SudokuGraph graph = new SudokuGraph(initialBoard);
            long startTime = System.nanoTime();

            SudokuNode solution = graph.buildGraphBFS();

            long endTime = System.nanoTime();

            if (solution != null) {
                System.out.println("\nBFS Solution");
                System.out.println(solution.getBoard());
                System.out.println("Time taken: " + (endTime - startTime) + " ns");

            } else {
                System.out.println("\nNo solution exists");
            }

            SudokuGraph graphDLS = new SudokuGraph(initialBoard);
            startTime = System.nanoTime();

            SudokuNode solutionDLS = graphDLS.solveDLS();

            endTime = System.nanoTime();

            if (solutionDLS != null) {
                System.out.println("\nDLS Solution:");
                System.out.println(solutionDLS.getBoard());
                System.out.println("Time taken: " + (endTime - startTime) + " ns");

            } else {
                System.out.println("\nNo solution exists");
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error reading puzzle file: " + e.getMessage());
        }
    }


    private static int[][] readPuzzleFromFile(String filename, int size) throws FileNotFoundException {
        int[][] puzzle = new int[size][size];
        Scanner scanner = new Scanner(new File(filename));

        /* for (int i = 0; i < size; i++) {
            String[] line = scanner.nextLine().trim().split(" ");
            for (int j = 0; j < size; j++) {
                puzzle[i][j] = Integer.parseInt(line[j]);
            }
        } */
       int row = 0;
       while (scanner.hasNextLine() && row < size){
        String line = scanner.nextLine().trim();
        //System.out.println("Raw line " + row + ": '" + line + "'");
        String[] values = line.split("\\s+");
        if (values.length != size){
            throw new IllegalArgumentException(
                "Row" + row + "has incorrect number of columns" + values.length
            );
        }
        for (int col = 0; col <size; col++){
            try {
                puzzle[row][col] = Integer.parseInt(values[col]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(
                    "Invalid number at row " + row + ", column " + col + ": " + values[col]
                );
            } 
        }
        row++;
        }
        if (row != size) {
        throw new IllegalArgumentException("The file contains " + row + " rows (expected " + size + ").");
        }

        scanner.close();
        return puzzle;
    }
}