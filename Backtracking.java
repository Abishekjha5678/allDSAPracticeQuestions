public class Backtracking {
    /* Problem word search Leetcode 79
     * Given an m x n grid of characters board and a string word, return true if word exists in the grid.
     * The word can be constructed from letters of sequentially adjacent cells,
     * where adjacent cells are horizontally or vertically neighboring. The same letter cell may not be used more than once.
     * 
     * Approoaches : 
     * step 1: iterate over the matrix m * n 
     * step 2: inside the second loop check  if given word[0] = matrix[i][j] 
     * step 3: if matched the perform DFS
     * step 3: inside find method take the direction 
     * step 4: everytime while doing DFS will mark the index is visited if not found the word will revert back in the index
     * 
     */
        // Directions to move in the 2D grid: right, down, up, left
        int [][] direction = {
            {0, 1},   // right
            {1, 0},   // down
            {-1, 0},  // up
            {0, -1}   // left
        };
    
        /**
         * Backtracking function to check if we can build the word from (i, j)
         * @param word The word we are searching for
         * @param board The 2D character grid
         * @param i Current row index
         * @param j Current column index
         * @param index The current index in the word we're trying to match
         * @return true if word can be formed starting from (i, j)
         */
        boolean backtrack(String word, char[][] board, int i, int j, int index) {
            // Base Case: Entire word is matched
            if (index == word.length()) return true;
    
            // Boundary or mismatch check
            if (i < 0 || j < 0 || i >= board.length || j >= board[0].length ||
                board[i][j] == '0' || board[i][j] != word.charAt(index)) {
                return false;
            }
    
            // Save current char and mark visited
            char ch = board[i][j];
            board[i][j] = '0'; // mark cell as visited
    
            // Explore all 4 directions
            for (int k = 0; k < 4; k++) {
                int next_i = i + direction[k][0];
                int next_j = j + direction[k][1];
    
                if (backtrack(word, board, next_i, next_j, index + 1)) {
                    return true;
                }
            }
    
            // Backtrack: unmark cell
            board[i][j] = ch;
            return false;
        }
    
        /**
         * Main function to initiate backtracking for each cell
         * @param board 2D grid of characters
         * @param word Word to search
         * @return true if word exists in the grid
         */
        public boolean exist(char[][] board, String word) {
            // Iterate through each cell in the grid
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    // Start backtracking if first char matches
                    if (board[i][j] == word.charAt(0) &&
                        backtrack(word, board, i, j, 0)) {
                        return true;
                    }
                }
            }
            return false;
        }
    
}
