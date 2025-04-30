import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class nQueens {

    /*
     * Time Complexity Analysis
        The number of nodes in the recursion tree:

        At each row, we try up to n columns → n choices per row.

        For n rows, the total number of possible board states we could explore = O(n!)

        Each time we validate a cell, we check:

        O(n) for column + O(n) for each diagonal = O(n) time per isValid() call.

        So:

        Total time = O(n!) (number of recursive calls) × O(n) (cost of checking validity)

        ⏱ Time Complexity: O(n! * n)
     */
    /*
     * Space Complexity Analysis
        Board size: O(n^2) for the char[][] board

        Call stack depth: O(n) for the recursive calls

        Result storage: In worst case, number of solutions is exponential (e.g., 92 for n = 8), so output space is O(n × k) where k is number of valid boards

        Space Complexity: O(n^2 + n) = O(n^2)
     */
    /**
     * Checks if placing a queen at (row, col) is valid.
     * It checks:
     * 1. Column above
     * 2. Left diagonal above
     * 3. Right diagonal above
     *
     * @param n     the size of the board
     * @param board current board state
     * @param row   row index to place the queen
     * @param col   column index to place the queen
     * @return true if it's safe to place the queen, false otherwise
     */
    boolean isValid(int n, char[][] board, int row, int col) {
        // Check column
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 'Q') return false;
        }

        // Check upper-left diagonal
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 'Q') return false;
        }

        // Check upper-right diagonal
        for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) {
            if (board[i][j] == 'Q') return false;
        }

        return true;
    }

    /**
     * Backtracking function to try placing queens row by row
     *
     * @param n        size of board
     * @param result   list to collect valid board configurations
     * @param nQueens  current board state
     * @param row      current row to place the queen
     */
    void solveNQueens(int n, List<List<String>> result, char[][] nQueens, int row) {
        if (row == n) {
            // All queens are placed successfully
            List<String> solution = new ArrayList<>();
            for (char[] rowArray : nQueens) {
                solution.add(new String(rowArray));
            }
            result.add(solution);
            return;
        }

        for (int col = 0; col < n; col++) {
            if (isValid(n, nQueens, row, col)) {
                nQueens[row][col] = 'Q'; // place queen
                solveNQueens(n, result, nQueens, row + 1); // recurse to next row
                nQueens[row][col] = '.'; // backtrack
            }
        }
    }

    /**
     * Entry point to solve the N-Queens problem
     *
     * @param n board size
     * @return all valid board configurations
     */
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> output = new ArrayList<>();
        char[][] nQueens = new char[n][n];

        // Initialize the board with '.'
        for (int i = 0; i < n; i++) {
            Arrays.fill(nQueens[i], '.');
        }

        solveNQueens(n, output, nQueens, 0);
        return output;
    }

    // GFG Rat in a Maze Problem - I

    // Directions: Right, Down, Up, Left
    int[][] direction = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};
    char[] ch = {'R', 'D', 'U', 'L'};

    /**
     * Backtracking helper to explore all valid paths in the maze
     *
     * @param maze   the maze grid with 1 as valid and 0 as blocked cell
     * @param i      current row index
     * @param j      current column index
     * @param result list to store all successful paths
     * @param sb     current path being constructed using directions
     */
    void backtrack(int[][] maze, int i, int j, ArrayList<String> result, StringBuilder sb) {
        // Check for invalid position or already visited (0) cell
        if (i < 0 || i >= maze.length || j < 0 || j >= maze[0].length || maze[i][j] == 0) return;

        // Reached destination cell (bottom-right corner)
        if (i == maze.length - 1 && j == maze[0].length - 1) {
            result.add(sb.toString());
            return;
        }

        // Mark current cell as visited
        int temp = maze[i][j];
        maze[i][j] = 0;

        // Try all 4 directions (R, D, U, L)
        for (int d = 0; d < 4; d++) {
            int newI = i + direction[d][0];
            int newJ = j + direction[d][1];
            sb.append(ch[d]); // choose
            backtrack(maze, newI, newJ, result, sb); // explore
            sb.deleteCharAt(sb.length() - 1); // un-choose
        }

        // Restore the cell for other paths
        maze[i][j] = temp;
    }

    /**
     * Main function to solve Rat in a Maze problem
     *
     * @param maze 2D grid representing maze
     * @return sorted list of all possible paths from top-left to bottom-right
     */
    public ArrayList<String> ratInMaze(int[][] maze) {
        ArrayList<String> result = new ArrayList<>();
        backtrack(maze, 0, 0, result, new StringBuilder());
        Collections.sort(result); // sort lexicographically if required
        return result;
    }
}
