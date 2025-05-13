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
}
