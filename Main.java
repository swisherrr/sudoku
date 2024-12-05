/**
 * Main Class
 * - Tests and times Sudoku solver implementations (BFS and DLS)
 * Based on research paper: "Comparison Analysis of Breadth First Search and Depth Limited Search Algorithms in Sudoku Game"
 * - by Tirsa Ninia Lina, Matheus Supriyanto Rumetna
 *
 * Zac Swisher, Sneha Patel, Jordano Liberato
 * CSC 301
 * 12/06/24
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.List;
public class Main {
    public static void main(String[] args) {
        String filename = "./boards/4solutions.txt";
        int size = 9;
        try {
            // read and initialize puzzle
            int[][] initialPuzzle = readPuzzleFromFile(filename,size);
            SudokuBoard initialBoard = new SudokuBoard(size);
            initialBoard.initializeBoard(initialPuzzle);

            System.out.println("Initial Puzzle:");
            System.out.println(initialBoard);

            // BFS Solutions
            SudokuGraph graph = new SudokuGraph(initialBoard);
            long startTime = System.nanoTime();
            List<SudokuNode> bfsSolutions = graph.buildGraphBFS();
            long endTime = System.nanoTime();

            System.out.println("\nBFS Solutions Found: " + bfsSolutions.size());
            for (int i = 0; i < bfsSolutions.size(); i++) {
                System.out.println("\nSolution " + (i + 1) + ":");
                System.out.println(bfsSolutions.get(i).getBoard());
            }
            System.out.println("BFS Time taken: " + (endTime - startTime) + " ns");

            // DLS Solutions
            SudokuGraph graphDLS = new SudokuGraph(initialBoard);
            startTime = System.nanoTime();
            SudokuNode solution = graphDLS.solveDLS();
            endTime = System.nanoTime();

            System.out.println("\nDLS Solutions Found: 1");
            System.out.println(solution.getBoard());
            System.out.println("DLS Time taken: " + (endTime - startTime) + " ns");


        } catch (FileNotFoundException e) {
            System.out.println("Error reading puzzle file: " + e.getMessage());
        }
    }


    private static int[][] readPuzzleFromFile(String filename, int size) throws FileNotFoundException {
        int[][] puzzle = new int[size][size];
        Scanner scanner = new Scanner(new File(filename));


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