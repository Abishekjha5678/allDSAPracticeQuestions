import java.util.ArrayList;
import java.util.Arrays;
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
}
