public class PalindromePartition {
        // this is my approaches getting TLE without memoization time complexity is O(2^n *n)+O(n^2)
        /*boolean dp[][];
        int backtracking(String s, int index, List<String>temp, int min){
            if(s.length()== index){
                return temp.size()-1;
            }
            int max=Integer.MAX_VALUE;
            for(int i=index;i<s.length();i++){
                if(dp[index][i]){
                    temp.add(s.substring(index, i+1));
                    max=backtracking(s, i+1, temp, min);
                    temp.remove(temp.size()-1);
                }
                min=Math.min(max,min);
            }
            return min;
            
        }
        public int minCut(String s) {
            int n = s.length();
            dp = new boolean[n][n];
            // calculated= new String[n];
            // Precompute all palindromic substrings
            for (int i = n - 1; i >= 0; i--) {
                for (int j = i; j < n; j++) {
                    if (s.charAt(i) == s.charAt(j)) {
                        if (j - i <= 2) {
                            dp[i][j] = true;
                        } else {
                            dp[i][j] = dp[i + 1][j - 1];
                        }
                    }
                }
            }
    
            return backtracking(s, 0, new ArrayList<>(),Integer.MAX_VALUE);
        }
        */
        
        /* DP + Memoization TIme 
        What is the time complexity?
        We have n possible index values (0 to n-1).
    
        For each index, we loop from index to n-1 (O(n) work per recursion call).
    
        And because of memoization, each index is solved only once.
        boolean[][] dp;
        int[] memo;
        
        int backtracking(String s, int index){
            if(index == s.length()) return -1; // no cuts needed at end
            
            if(memo[index] != -1) return memo[index];
            
            int minCuts = Integer.MAX_VALUE;
            for(int i = index; i < s.length(); i++){
                if(dp[index][i]){
                    int cuts = 1 + backtracking(s, i+1);
                    minCuts = Math.min(minCuts, cuts);
                }
            }
            
            memo[index] = minCuts;
            return minCuts;
        }
        
        public int minCut(String s) {
            int n = s.length();
            dp = new boolean[n][n];
            memo = new int[n];
            Arrays.fill(memo, -1);
            
            // Precompute palindrome substrings
            for (int i = n - 1; i >= 0; i--) {
                for (int j = i; j < n; j++) {
                    if (s.charAt(i) == s.charAt(j)) {
                        if (j - i <= 2) {
                            dp[i][j] = true;
                        } else {
                            dp[i][j] = dp[i + 1][j - 1];
                        }
                    }
                }
            }
    
            return backtracking(s, 0);
        }
    }
    */
    // Here is the bottom-up code
    
        public int minCut(String s) {
            int n = s.length();
            boolean[][] dp = new boolean[n][n];
            
            // Precompute palindrome table
            for(int i = n-1; i >=0; i--){
                for(int j = i; j < n; j++){
                    if(s.charAt(i) == s.charAt(j)){
                        if(j-i<=2){
                            dp[i][j] = true;
                        } else {
                            dp[i][j] = dp[i+1][j-1];
                        }
                    }
                }
            }
            
            int[] minCuts = new int[n+1];
            minCuts[n] = -1; // base case: no cuts needed after end
            
            for(int i = n-1; i >=0; i--){
                int min = Integer.MAX_VALUE;
                for(int j = i; j < n; j++){
                    if(dp[i][j]){
                        min = Math.min(min, 1 + minCuts[j+1]);
                    }
                }
                minCuts[i] = min;
            }
            
            return minCuts[0];
        }
}
