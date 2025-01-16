# Sudoku Solver

A Java implementation of a Sudoku puzzle solver that utilizes both Breadth-First Search (BFS) and Depth-Limited Search (DLS) algorithms. Based on research by Tirsa Ninia Lina and Matheus Supriyanto Rumetna, implemented and extended by Zac Swisher, Sneha Patel, and Jordano Liberato.

## Features

- Supports multiple grid sizes:
  - 9x9 (standard Sudoku)
  - 16x16 (super Sudoku)
- Implements two solving algorithms:
  - Breadth-First Search (BFS) - finds all possible solutions
  - Depth-Limited Search (DLS) - finds single solution efficiently
- Reads puzzles from text files
- Performance timing for algorithm comparison
- Command-line output with detailed solution steps

## Project Structure

```
sudoku-solver/
├── src/
│   ├── Main.java
│   ├── SudokuBoard.java
│   ├── SudokuGraph.java
│   ├── DLSState.java
│   └── SudokuNode.java
├── boards/
│   ├── easy9x9.txt
│   ├── medium9x9.txt
│   ├── hard9x9.txt
│   ├── easy16x16.txt
│   └── medium16x16.txt
└── README.md
```

## Input Format

The program reads Sudoku puzzles from text files. Each file should contain space-separated numbers with:
- 0 representing empty cells
- Numbers 1-9 for 9x9 puzzles
- Numbers 1-16 for 16x16 puzzles

Example (9x9):
```
0 0 0 2 6 0 7 0 1
6 8 0 0 7 0 0 9 0
1 9 0 0 0 4 5 0 0
...
```

## Usage

Compile and run the program:
```bash
javac Main.java
java Main
```

The program will process all puzzles in the specified category (9x9 or 16x16) and output:
- Initial puzzle state
- BFS solutions (all possible solutions)
- DLS solution (single solution)
- Execution time for each algorithm

## Implementation Details

### Main Class
- Handles file reading and program execution
- Times algorithm performance
- Manages output formatting

### Key Components
- `SudokuBoard`: Represents the Sudoku grid and game rules
- `SudokuGraph`: Implements BFS and DLS algorithms
- `SudokuNode`: Represents a state in the solution space

## Performance Characteristics

- BFS:
  - Finds all possible solutions
  - Higher memory usage
  - Slower for larger puzzles
- DLS:
  - Finds single solution
  - More memory efficient
  - Faster execution time

## Requirements

- Java 8 or higher
- Text files containing valid Sudoku puzzles
- Sufficient memory for larger puzzles (especially 16x16)

## Build and Test

```bash
# Compile all Java files
javac *.java

# Run with 9x9 puzzles
java Main

# To switch to 16x16 puzzles, modify the array selection in Main.java
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/NewFeature`)
3. Commit your changes (`git commit -m 'Add some NewFeature'`)
4. Push to the branch (`git push origin feature/NewFeature`)
5. Open a Pull Request

## Authors

- **Zac Swisher**
- **Sneha Patel**
- **Jordano Liberato**

Based on research by:
- Tirsa Ninia Lina
- Matheus Supriyanto Rumetna

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

## Acknowledgments

- Original research from the Bulletin of Computer Science and Electrical Engineering
