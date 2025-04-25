
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    /* Leetcode Problem : 17 letter combination of a phone number
     * approach is same as the above
     * 
     */
    
    

    // Solution
    void backtrack(String digits, int index,Map<Character, String> mp,List<String> res,StringBuilder comb) {
        if (digits.length() == index) {
            res.add(comb.toString());
            return;
        }
        String str= mp.get(digits.charAt(index));
        for (char letter : str.toCharArray()) {
            comb.append(letter);
            backtrack(digits,index+1, mp, res, comb);
            comb.deleteCharAt(comb.length() - 1);
        }
        System.out.println();
        for(String s: res)System.out.print(s+" ");
    }
    // dry run input digits = "23" n= 2 total number of call = 3^size(letter) = 27

    /*Time Complexity:
    If there are n digits and each digit maps to m letters (worst case m=4, like for '7'/'9'), then:

    T(n) = O(mⁿ) combinations generated

    Each combination takes O(n) to build/copy

    Final Time Complexity = O(mⁿ * n)

    Space Complexity:

    Stack depth is n (due to recursion)

    Result space = O(mⁿ * n)

    Using StringBuilder avoids new string creation in each call → efficient*/
    /* first call : [ index =0 ,comb = "a"]
    second call: [index =1, comb="ad"]
    third call : [ index=2, comb="ad"] res=["ab"] return; and comb.deleteCharAt(d);
    forth call : [ index= 2, comb="ae"] res=["ae"] return; and comb.deleteCharAt(e);
    fifth call : [ index= 2, comb="ae"] res=["af"] return; and comb.deleteCharAt(f);
    loop end for 2 now back to a again and removed a;
    sixth call : [ index= 1, comb="b"] res=["b"]
    7th call : [ index= 2, comb="bd"] res=["bd"] return; and comb.deleteCharAt(d);
    8th call : [ index= 2, comb="be"] res=["be"] return; and comb.deleteCharAt(e);
    so on
    */
    public List<String> letterCombinations(String digits) {
        List<String> res = new ArrayList<>();
        if (digits == null || digits.length() == 0) {
            return res;
        }
        Map<Character, String> digitToLetters = new HashMap<>();
        digitToLetters.put('2', "abc");
        digitToLetters.put('3', "def");
        digitToLetters.put('4', "ghi");
        digitToLetters.put('5', "jkl");
        digitToLetters.put('6', "mno");
        digitToLetters.put('7', "pqrs");
        digitToLetters.put('8', "tuv");
        digitToLetters.put('9', "wxyz");
        
        backtrack(digits,0, digitToLetters, res, new StringBuilder());
        return res;
    }
}
