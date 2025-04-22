
import java.util.Arrays;

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
}
