package  StacksAndQueue;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class Stack {

    // Function to find the index of the Next Smaller Element to the Left (NSL) for each element
    int[] nextSmallerToLeft(int[] nums, int n) {
        Deque<Integer> st = new ArrayDeque<>();
        int result[] = new int[n];
        Arrays.fill(result, -1);  // If there's no smaller element to the left, use -1

        for (int i = 0; i < n; i++) {
            // Pop elements from stack until the current element is greater than stack's top
            while (!st.isEmpty() && nums[st.peek()] >= nums[i]) {
                st.pop();
            }
            if (!st.isEmpty()) {
                result[i] = st.peek();  // Index of the smaller element to the left
            }
            st.push(i);  // Push current index to stack
        }
        return result;
    }

    // Function to find the index of the Next Smaller Element to the Right (NSR) for each element
    int[] nextSmallerToRight(int[] nums, int n) {
        Deque<Integer> st = new ArrayDeque<>();
        int result[] = new int[n];
        Arrays.fill(result, n);  // If there's no smaller element to the right, use n (out of bounds)

        for (int i = n - 1; i >= 0; i--) {
            // Pop elements from stack until we find a smaller one
            while (!st.isEmpty() && nums[st.peek()] > nums[i]) {
                st.pop();
            }
            if (!st.isEmpty()) {
                result[i] = st.peek();  // Index of the smaller element to the right
            }
            st.push(i);  // Push current index to stack
        }
        return result;
    }

    // Main function to calculate sum of subarray minimums
    public int sumSubarrayMins(int[] arr) {
        int n = arr.length;
        int[] nsl = nextSmallerToLeft(arr, n);  // NSL: Previous smaller elements' indices
        int[] nsr = nextSmallerToRight(arr, n); // NSR: Next smaller elements' indices

        int mod = (int) 1e9 + 7;
        long res = 0;

        for (int i = 0; i < n; i++) {
            int leftSpan = i - nsl[i];      // Number of elements on the left including arr[i]
            int rightSpan = nsr[i] - i;     // Number of elements on the right including arr[i]

            // Each arr[i] appears in leftSpan * rightSpan subarrays as the minimum
            long count = (long) arr[i] * leftSpan % mod * rightSpan % mod;
            res = (res + count) % mod;
        }

        return (int) res;
    }

    // LeetCode  2104 Sum of Subarray Ranges
   /*  Approaches : 
        STEP 1: Next Smaller to Left (NSL)
        STEP 2: Next Smaller to Right (NSR)
        STEP 3: Next Greater to Left (NGL)
        STEP 4: Next Greater to Right (NGR)
        STEP 5: Main method to compute subArrayRanges

   */
        int[] nextGreaterToLeft(int []nums, int n){
            Deque<Integer> st = new ArrayDeque<>();
            int result[]=new int[n];
            Arrays.fill(result,-1);
            for(int i=0;i<n;i++){
                while(!st.isEmpty() && nums[st.peek()] <= nums[i] )st.pop();
                if(!st.isEmpty())result[i]=st.peek();
                st.push(i);
            }
            return result;
        }
        int[] nextGreaterToRight(int []nums, int n){
            Deque<Integer> st = new ArrayDeque<>();
            int result[]=new int[n];
            Arrays.fill(result,n);
            for(int i=n-1;i>=0;i--){
                while(!st.isEmpty() && nums[st.peek()] < nums[i])st.pop();
                if(!st.isEmpty())result[i]=st.peek();
                st.push(i);
            }
            return result;
        }
        public long subArrayRanges(int[] nums) {
        int n = nums.length;

        // Preprocessing steps using Monotonic Stack techniques
        int NSL[] = nextSmallerToLeft(nums, n);
        int NSR[] = nextSmallerToRight(nums, n);
        int NGR[] = nextGreaterToRight(nums, n);
        int NGL[] = nextGreaterToLeft(nums, n);

        long minSum = 0, maxSum = 0;

        // For each element nums[i], compute:
        for (int i = 0; i < n; i++) {
            int leftSmaller = i - NSL[i];   // Number of elements to the left where nums[i] is min
            int rightSmaller = NSR[i] - i;  // Number of elements to the right where nums[i] is min
            long minCount = (long) leftSmaller * rightSmaller;
            minSum += minCount * nums[i];

            int leftGreater = i - NGL[i];   // Number of elements to the left where nums[i] is max
            int rightGreater = NGR[i] - i;  // Number of elements to the right where nums[i] is max
            long maxCount = (long) leftGreater * rightGreater;
            maxSum += maxCount * nums[i];
        }

        // Return the total difference
        return maxSum - minSum;
    }
    
}
