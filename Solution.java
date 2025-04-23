
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {
    /* Problem 
    You are given an array of integers nums and an integer target.

    Return the number of non-empty subsequences of nums such that the sum of 
    the minimum and maximum element on it is less or equal to target. 
    Since the answer may be too large, return it modulo 109 + 7.
    */
    /* Approaches :
     * Step:1 first sort all the array after this we will get the
     *  minimum at index 0 and max at index n-1 
     * Step 2: take 2 pointer and check if sum of min and max is greater than target then r-- else l++
     */

     private int powMod(int base, int exp, int mod) {
        long result = 1;
        long b = base;
        
        while (exp > 0) {
            if ((exp & 1) == 1) {
                result = (result * b) % mod;
            }
            b = (b * b) % mod;
            exp >>= 1;
        }

        return (int) result;
    }
    public int countSubsequenceWithTargetSum(int []nums, int target){
        Arrays.sort(nums);
        int count=0;
        int l=0, r=nums.length-1;
        int mod= (int)1e9+7;
        while(l<=r){
            if(nums[l]+nums[r] >target)r--;
            else {
                count= (count+ powMod(2,r-l,mod))% mod;
                l++;
            }
        }
        return count;
    }

    /* problem : 
     * Given an array nums and an integer k.Return the number of non-empty subsequences
     *  of nums such that the sum of all elements in the subsequence is equal to k.
     * 
     * Input : nums = [4, 9, 2, 5, 1] , k = 10

        Output : 2

        Explanation : The possible subsets with sum k are [9, 1] , [4, 5, 1].

        Input : nums = [4, 2, 10, 5, 1, 3] , k = 5

        Output : 3

        Explanation : The possible subsets with sum k are [4, 1] , [2, 3] , [5].
     */
    /*
     Approaches : 
        Step 1: generate all the subsequences and keep adding and check if the subsequence sum is k then increase the counter;

    */
    
    int backtrack(int[] arr, int index, int sum, int target) {
        if (index == arr.length) {
            return (sum == target) ? 1 : 0;
        }

        // Include current element
        int include = backtrack(arr, index + 1, sum + arr[index], target);
        // Exclude current element
        int exclude = backtrack(arr, index + 1, sum, target);

        return include + exclude;
    }
    public int countSubsequenceWithTargetSumK(int[] arr, int target){
        return backtrack(arr, 0, 0, target);
        // return count.get();
    }
    
    /*problem // 39. Combination Sum  leetcode
     *  approaches: 
     * Step 1: everytime we've two options to i.e to pick the same element multiple time
     * or we'can choose the next element of the array. 
     * Time complexity of this approaches is O(2^n * target) because we can choose sinlge element multiple times
    */
    void combination(int []arr, int index, int target, List<List<Integer>> result, List<Integer> temp){
        if(index == arr.length){
            if(target == 0)result.add(new ArrayList<>(temp));
            return;
        }
        if (0 <=target ) {
            temp.add(arr[index]);
            combination(arr, index, target - arr[index], result, temp);
            temp.remove(temp.size() - 1);
        }
        combination(arr, index+1, target, result, temp);
        
        
    }
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result= new ArrayList<>();
        // var map=new HashMap<Integer, Integer>();

        // map.put(1,2);
        // for(var mp: map.entrySet()){
        //     System.out.println(mp);
        // }
        
        combination(candidates, 0, target, result, new ArrayList<>());
        return result;

    }

    /*Time Complexity	O(C(n, k) × k)
    Space Complexity	O(k) (excluding output)
    Output Space Complexity	O(C(n, k) × k) */
    // Leetcode : 77 Combination
    /*Approach : 
     * Step-1 : generate all the combination from 1 to n 
     */
    void backtrack(int n, int index,int target, List<List<Integer>> result, List<Integer> temp){
        if(temp.size()==target){
            result.add(new ArrayList<>(temp));
            return;
        }
        for(int i=index;i<=n;i++){
            temp.add(i);
            backtrack(n,i+1, target, result,temp);
            temp.remove(temp.size()-1);
        }
    }
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(n,1,k,result,new ArrayList<>());
        return result;
    }
}
