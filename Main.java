
import java.util.Scanner;



public class Main {
    public static void main(String[] args){
        // System.out.println("my first program");
        // System.out.println("my first program");
        /* Solution sol= new Solution();
        int arr[]={ 1,2,3,4,5};
        int k=6;
        System.out.println(sol.countSubsequenceWithTargetSum(arr, k));

        int arr2[]={ 1,2,3,4,5};
        int k2=9;
        System.out.println(sol.countSubsequenceWithTargetSum(arr2, k2));

        int arr3[]={4, 9, 2, 5, 1};
        int k3=10;
        System.out.println(sol.countSubsequenceWithTargetSumK(arr3, k3));

        int arr[]={2,3,6,7};
        int k=7;
        List<List<Integer>> result =sol.combinationSum(arr, k);
        for(List<Integer> list: result){
            for(int i:list)
                System.out.print(i+" ");
            System.out.println();
        } */
       Backtracking backtracking= new Backtracking();
       Scanner sc = new Scanner(System.in);
        System.out.println("Enter the rows : ");
        int rows = sc.nextInt();

        System.out.println("Enter the cols: ");
        int cols = sc.nextInt();

        sc.nextLine(); // consume the leftover newline

        char[][] grid = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            System.out.println("Enter "+i+1+ " rows value : ");
            String line = sc.nextLine(); // e.g., "abc"
            for (int j = 0; j < cols; j++) {
                grid[i][j] = line.charAt(j);
            }
        }

        String word = sc.nextLine();

        System.out.println(backtracking.exist(grid, word));

        
    }
}
