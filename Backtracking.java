import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
         * Time	O(N × M × 3^L)
         * Space O(L)
         * where L is the length of word
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


        public List<String> findWords1(char[][] board, String[] words) {
            List<String> result=new ArrayList<>();
            for(String s: words){
                for(int i=0;i<board.length;i++){
                    for(int j=0;j<board[0].length;j++){
                        if(board[i][j]==s.charAt(0) && backtrack(s, board, i, j, i))
                            result.add(s);
                    }
                }
            }
          return result;
        }
        

            // Directions: Right, Down, Up, Left
            /*int[][] direction = {
                    { 0, 1 },  // move right
                    { 1, 0 },  // move down
                    { -1, 0 }, // move up
                    { 0, -1 }  // move left
            }; */
        
            /*
            -----------------------------------------------------------------------------------------
            1st Approach: Simple Backtracking for each word individually
            -----------------------------------------------------------------------------------------
        
            - For each word in the list:
              - We start a search from every cell in the board.
              - If the first character matches, we try to find the complete word recursively.
            
            - backtrack function:
              - Base Case: If index == word.length(), it means the entire word is found.
              - If the current position is out of bounds or the character mismatches, return false.
              - Mark the current cell as visited (by changing it temporarily to '0').
              - Explore in 4 possible directions (right, down, up, left).
              - If any path returns true, immediately return true.
              - After exploration, backtrack by restoring the original character.
        
            - Important: We pass index+1 while moving to the next character.
        
            - Time Complexity:
              For each word: O(m * n * 4^L), where L = length of word, m and n are board dimensions.
        
            */
           /*  boolean backtrackWordSearch2(String word, char[][] board, int i, int j, int index) {
                // Base Case: Entire word matched
                if (index == word.length()) 
                    return true;
        
                // Out of bounds or mismatch or already visited cell
                if (i < 0 || j < 0 || i >= board.length || j >= board[0].length ||
                        board[i][j] == '0' || board[i][j] != word.charAt(index)) {
                    return false;
                }
        
                // Save current char and mark visited
                char ch = board[i][j];
                board[i][j] = '0'; // mark this cell visited
        
                // Explore all 4 directions
                for (int k = 0; k < 4; k++) {
                    int next_i = i + direction[k][0];
                    int next_j = j + direction[k][1];
        
                    if (backtrack(word, board, next_i, next_j, index + 1)) {
                        board[i][j] = ch; // important: restore before returning
                        return true;
                    }
                }
        
                // Backtrack: restore the original character
                board[i][j] = ch;
                return false;
            }
        
            /*
            This findWords method calls backtrack for each word.
            *//* 
            public List<String> findWords(char[][] board, String[] words) {
                List<String> result = new ArrayList<>();
                for (String s : words) {
                    boolean found = false; // avoid adding same word multiple times
                    for (int i = 0; i < board.length && !found; i++) {
                        for (int j = 0; j < board[0].length && !found; j++) {
                            if (board[i][j] == s.charAt(0) && backtrack(s, board, i, j, 0)) {
                                result.add(s);
                                found = true;
                            }
                        }
                    }
                }
                return result;
            }
            */
            /*
            -----------------------------------------------------------------------------------------
            2nd Approach: Optimized using Trie + DFS
            -----------------------------------------------------------------------------------------
        
            - Build a Trie from all the words.
              (TrieNode: 26 children (for 'a' to 'z') and a word field to store complete words at nodes.)
        
            - Then for every cell in the board:
              - Perform DFS starting from that cell.
              - Move only if current character exists as a child in the Trie.
              - If we reach a node whose word is not null, it means we found a valid word.
                - Add that word to the result and mark word=null to avoid duplicates.
        
            - dfs function:
              - Base Case:
                - Out of bounds
                - Visited cell
                - Current character is not a child in Trie.
              - Mark cell visited by replacing with '1' temporarily.
              - Explore 4 directions recursively.
              - After exploring, restore original character (backtrack).
        
            - Time Complexity:
              - Much better than individual backtracking, because we only follow paths that match the prefix.
        
            */
            
            // TrieNode definition
            class TrieNode {
                TrieNode[] children = new TrieNode[26];
                String word = null; // store full word when a word ends here
            }
        
            // Build Trie from list of words
            TrieNode buildTrie(String[] words) {
                TrieNode root = new TrieNode();
                for (String str : words) {
                    TrieNode node = root;
                    for (char ch : str.toCharArray()) {
                        if (node.children[ch - 'a'] == null) {
                            node.children[ch - 'a'] = new TrieNode();
                        }
                        node = node.children[ch - 'a'];
                    }
                    node.word = str; // mark complete word at the end node
                }
                return root;
            }
        
            // DFS using Trie
            void dfs(char[][] board, int i, int j, TrieNode root, List<String> result) {
                // Base Case: Out of bounds
                if (i < 0 || j < 0 || i >= board.length || j >= board[0].length) {
                    return;
                }
        
                char ch = board[i][j];
        
                // If visited ('1') or no path in Trie, return
                if (ch == '1' || root.children[ch - 'a'] == null) {
                    return;
                }
        
                root = root.children[ch - 'a'];
        
                // Found a word
                if (root.word != null) {
                    result.add(root.word);
                    root.word = null; // avoid duplicate
                }
        
                // Mark as visited
                board[i][j] = '1';
        
                // Explore all 4 directions
                for (int d = 0; d < 4; d++) {
                    int next_i = i + direction[d][0];
                    int next_j = j + direction[d][1];
                    dfs(board, next_i, next_j, root, result);
                }
        
                // Backtrack: Restore original character
                board[i][j] = ch;
            }
        
            /*
            This findWords method uses Trie + DFS optimization.
            */
            public List<String> findWords(char[][] board, String[] words) {
                List<String> result = new ArrayList<>();
                TrieNode root = buildTrie(words);
        
                for (int i = 0; i < board.length; i++) {
                    for (int j = 0; j < board[0].length; j++) {
                        dfs(board, i, j, root, result);
                    }
                }
        
                return result;
            }
        
        /*
First Approach (Brute Force DFS without memoization)
-----------------------------------------------------
- Try all possible paths using simple DFS.
- Only two directions allowed: right (0,1) and down (1,0).
- Mark the cell as visited temporarily by setting it to 9 to avoid revisiting.
- Base case:
    - If we reach the bottom-right cell, return 1 (found a path).
    - If out of bounds or hit an obstacle (1) or visited (9), return 0.
- After exploring, backtrack by restoring the original value.

Time Complexity: O(2^(m+n))  --> Very high, leads to TLE (Time Limit Exceeded) for large grids.
Space Complexity: O(m*n) due to recursion stack.

Code:

    int direction[][]= {{0,1},{1,0}};
    int dfs(int [][]arr, int i, int j){
        if(i<0 || j<0 || i>=arr.length || j>=arr[0].length || arr[i][j] == 9 || arr[i][j] == 1) return 0;
        if(i == arr.length-1 && j == arr[0].length-1) return 1;
        
        int temp = arr[i][j];
        arr[i][j] = 9; // Mark visited
        
        int possible = 0;
        for(int k = 0; k < 2; k++){
            int index_i = i + direction[k][0];
            int index_j = j + direction[k][1];
            possible += dfs(arr, index_i, index_j);
        }
        
        arr[i][j] = temp; // Backtrack
        return possible;
    }

    public int uniquePathsWithObstacles(int[][] arr) {
        return dfs(arr, 0, 0);
    }
*/

///////////////////////////////////////////////////////////

/*
Optimized Approach (DFS + Memoization / DP)
--------------------------------------------
- Use a memoization table (2D dp array) to store already computed answers.
- dp[i][j] = number of unique paths from (i,j) to the destination.
- Avoid recalculating paths from the same cell.

Key Points:
- If current cell is an obstacle (1), return 0.
- If we reach destination, return 1.
- If already computed (dp[i][j] != -1), return saved value.

Time Complexity: O(m*n)  --> Because each cell is visited once at most.
Space Complexity: O(m*n)  --> For memoization table + recursion stack.

*/


    int[][] dp;

    int dfs(int[][] arr, int i, int j) {
        if (i < 0 || j < 0 || i >= arr.length || j >= arr[0].length || arr[i][j] == 1)
            return 0;
        
        if (i == arr.length - 1 && j == arr[0].length - 1)
            return 1;

        if (dp[i][j] != -1)
            return dp[i][j]; // Return memoized result

        int right = dfs(arr, i, j + 1);
        int down = dfs(arr, i + 1, j);

        dp[i][j] = right + down;
        return dp[i][j];
    }

    public int uniquePathsWithObstacles(int[][] arr) {
        int m = arr.length;
        int n = arr[0].length;
        dp = new int[m][n];
        for (int[] row : dp) {
            Arrays.fill(row, -1); // Initialize memoization table with -1
        }

        return dfs(arr, 0, 0);
    }

    
    
}
