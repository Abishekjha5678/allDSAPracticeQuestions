package  StacksAndQueue;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Stack;


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

    /**
 * This class provides a method to remove `k` digits from a given number (represented as a string)
 * to make it the smallest possible number.
 */
    // LEETCODE 402. Remove K Digits

    /**
     * Removes exactly `k` digits from the input number string `num` such that the resulting number is the smallest possible.
     *
     * <p><strong>Approach:</strong>
     * <ul>
     *   <li>We use a greedy strategy with a monotonic increasing stack (Deque).</li>
     *   <li>For each digit in the input:
     *     <ul>
     *       <li>While `k > 0`, and the last digit in the stack is greater than the current digit,
     *           we remove the last digit from the stack. This ensures that higher digits are removed early to allow smaller digits to take their place.</li>
     *       <li>Add the current digit to the stack.</li>
     *     </ul>
     *   </li>
     *   <li>If any digits are still to be removed after the traversal (`k > 0`), remove from the end of the stack.</li>
     *   <li>Construct the final number by removing any leading zeros.</li>
     *   <li>If the final result is an empty string, return "0".</li>
     * </ul>
     *
     * <p><strong>Time Complexity:</strong> O(n), where n is the length of the number string.
     * <br><strong>Space Complexity:</strong> O(n) for the stack.
     *
     * @param num The original number string
     * @param k   The number of digits to remove
     * @return The smallest number possible after removing `k` digits
     */
    public String removeKdigits(String num, int k) {
        Deque<Character> stack = new ArrayDeque<>();
        
        // Traverse through all digits
        for (char digit : num.toCharArray()) {
            // Remove digits from the stack while they are larger than the current digit
            // and we still have digits to remove
            while (k > 0 && !stack.isEmpty() && stack.peekLast() > digit) {
                stack.pollLast();
                k--;
            }
            stack.offerLast(digit);
        }

        // If we still need to remove digits, remove from the end
        while (k-- > 0 && !stack.isEmpty()) {
            stack.pollLast();
        }

        // Build the result string while removing leading zeros
        StringBuilder result = new StringBuilder();
        boolean leadingZero = true;

        for (char ch : stack) {
            if (leadingZero && ch == '0') continue;
            leadingZero = false;
            result.append(ch);
        }

        // Return "0" if all digits are removed or the result is empty
        return result.length() == 0 ? "0" : result.toString();
    }

/**
 * This class provides a solution to the asteroid collision problem.
 * Asteroids are represented by integers in an array where:
 * - Positive values move right
 * - Negative values move left
 * 
 * When two asteroids collide:
 * - The smaller one explodes
 * - If both are equal, both explode
 * - Asteroids moving in the same direction never collide
 */

    /**
     * Simulates asteroid collisions and returns the state of asteroids that survive.
     *
     * @param asteroids An array of integers where positive represents an asteroid moving to the right,
     *                  and negative represents an asteroid moving to the left.
     * @return An array of integers representing the remaining asteroids after all collisions.
     */
    public int[] asteroidCollision(int[] asteroids) {
        Stack<Integer> stack = new Stack<>();

        // Iterate through each asteroid
        for (int a : asteroids) {
            boolean destroyed = false;

            // Process collisions: only occurs if top of stack is moving right (positive)
            // and current asteroid is moving left (negative)
            while (!stack.isEmpty() && stack.peek() > 0 && a < 0) {
                int sum = a + stack.peek();

                if (sum < 0) {
                    // Incoming asteroid is stronger (more negative), pop the top and keep checking
                    stack.pop();
                } else if (sum == 0) {
                    // Equal strength -> both destroy each other
                    stack.pop();
                    destroyed = true;
                    break;
                } else {
                    // Stack top is stronger -> current asteroid destroyed
                    destroyed = true;
                    break;
                }
            }

            // If the current asteroid wasn't destroyed, push it onto the stack
            if (!destroyed) {
                stack.push(a);
            }
        }

        // Convert stack to result array (stack is LIFO, so reverse the order)
        int[] result = new int[stack.size()];
        for (int i = result.length - 1; i >= 0; i--) {
            result[i] = stack.pop();
        }

        return result;
    }
    //42. Trapping Rain Water
    // Approach 1 Time O(N) and Space O(1)

    /**
     * Calculates the total water trapped using two-pointer approach.
     *
     * @param height An array representing elevation map.
     * @return Total units of water trapped.
     */
    public int trap(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int leftMax = height[left];
        int rightMax = height[right];
        int water = 0;

        while (left < right) {
            if (leftMax < rightMax) {
                left++;
                leftMax = Math.max(leftMax, height[left]);
                water += leftMax - height[left]; // Water trapped at this index
            } else {
                right--;
                rightMax = Math.max(rightMax, height[right]);
                water += rightMax - height[right]; // Water trapped at this index
            }
        }

        return water;
    }

    // Approach 2 Time O(N) and Space O(N)

    /**
     * Precomputes the max height to the left of each index.
     */
    int[] leftMaxElement(int[] arr, int n) {
        int[] res = new int[n];
        res[0] = arr[0];
        for (int i = 1; i < n; i++) {
            res[i] = Math.max(res[i - 1], arr[i]);
        }
        return res;
    }

    /**
     * Precomputes the max height to the right of each index.
     */
    int[] rightMaxElement(int[] arr, int n) {
        int[] res = new int[n];
        res[n - 1] = arr[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            res[i] = Math.max(res[i + 1], arr[i]);
        }
        return res;
    }

    /**
     * Calculates the total water trapped using precomputed arrays.
     *
     * @param height An array representing elevation map.
     * @return Total units of water trapped.
     */
    public int trapRain(int[] height) {
        int n = height.length;
        if (n == 0) return 0;

        int[] left = leftMaxElement(height, n);
        int[] right = rightMaxElement(height, n);

        int ans = 0;
        for (int i = 0; i < n; i++) {
            int waterAtI = Math.min(left[i], right[i]) - height[i];
            if (waterAtI > 0)
                ans += waterAtI;
        }
        return ans;
    }

    // Leetcode - 239 Sliding window maximum
        /* Approach: 
         * step-1: iterate like a fixd size sliding window 
         * step-2: while iterating keep checking the max element and put in queue
         * step-3: once window size hit then remove from the front of queue
         * step-3 Start filling result from window [k-1] onward
         */

        public int[] maxSlidingWindow(int[] nums, int k) {
            int n = nums.length;
            int[] result = new int[n - k + 1];
            Deque<Integer> deque = new ArrayDeque<>(); // Stores indices
            int index = 0;
    
            for (int j = 0; j < n; j++) {
                // Remove indices out of current window
                if (!deque.isEmpty() && deque.peekFirst() <= j - k) {
                    deque.pollFirst();
                }
    
                // Maintain decreasing order in deque
                while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[j]) {
                    deque.pollLast();
                }
    
                // Add current index
                deque.offerLast(j);
    
                // Start filling result from window [k-1] onward
                if (j >= k - 1) {
                    result[index++] = nums[deque.peekFirst()];
                }
            }
    
            return result;
        }

        // LeetCode’s 901. Online Stock Span
        /*
         * Step 1:
            Initialize days = 1 (today counts).

        Step 2:
            While the top of stack has price <= 85, we pop it and add its span to days.

            Why?
            Because that previous price (and all its span days) were less than today’s price — so all of them count toward today’s span.

        Step 3:
            After collecting all applicable days, push {price, days} into stack for future reference.
        Example Trace:
            Input:  [100, 80, 60, 70, 60, 75, 85]
            Output: [1,   1,  1,  2,  1,  4,  6]

            next(100) → Stack: [100, 1]
            next(80)  → Stack: [100, 1], [80, 1]
            next(60)  → Stack: [100, 1], [80, 1], [60, 1]
            next(70)  → Stack: [100, 1], [80, 1], [70, 2] ← 70 > 60, so we pop 60 and days += 1


         */

    class StockSpanner {
    Stack<int[]> st;

    public StockSpanner() {
        st = new Stack<>();
    }

    public int next(int price) {
        int days = 1;

        while (!st.isEmpty() && st.peek()[0] <= price) {
            days += st.pop()[1];
        }

        st.push(new int[]{price, days});
        return days;
    }
    }
    
    //84. Largest Rectangle in Histogram
    //Approach 1 Time O(n) & space O(N)
    /*
     * Step 1 : find next smaller to left
     * step 2 : find next greater to right
     * step 3 : iterate over the array and arr[i]* (nsr[i]-nsl[i]-1)
     */
    int[] NSL(int []arr,int n){
        int []res=new int[n];
        Arrays.fill(res,-1);
        Stack<Integer> st=new Stack<>();
        for(int i=0;i<n;i++){
            while(!st.isEmpty() && arr[st.peek()]>=arr[i])st.pop();
            if(!st.isEmpty())res[i]=st.peek();
            st.push(i);
        }
        return res;
    }

    int[] NSR(int []arr,int n){
        int []res=new int[n];
        Arrays.fill(res,n);
        Stack<Integer> st=new Stack<>();
        for(int i=n-1;i>=0;i--){
            while(!st.isEmpty() && arr[st.peek()]>=arr[i])st.pop();
            if(!st.isEmpty())res[i]=st.peek();
            st.push(i);
        }
        return res;
    }
    public int largestRectangleArea(int[] heights) {
        int n=heights.length;
        int []nsl=NSL(heights, n);
        int nsr[]=NSR(heights, n);
        int res=0;
        for(int i=0;i<n;i++){
            res=Math.max(res, heights[i]*(nsr[i]-nsl[i]-1));
        }
        return res;
    }

    // Approach 2
    /*
     * step 1: iterate over the array and keek the index of arr in stack
     * step 2: take one while loop inside it and check if the current element is <
     * step 3: On finding a smaller height, pop and calculate areas.
     * Ensure final cleanup by adding one extra iteration with height 0
     * Note indirectly we're doing the same logic here also NSL and NSR
     */

     public int largestRectangleArea(int[] heights) {
        Stack<Integer> st=new Stack<>();
        int max=0;
        for(int i=0;i<=heights.length;i++){
            int currentHeight = i == heights.length ? 0 : heights[i];
            while(!st.isEmpty() && currentHeight < heights[st.peek()]){
                int height = heights[st.pop()];
                int width = st.isEmpty() ? i : i - st.peek() -1;
                max = Math.max(max, height * width);
            }
            st.push(i);
        }
    return max;
    }


    /*
     * 85. Maximal Rectangle
        my approach for the question is 
        step 1: take one 2d array to calculate the i-1 the index
        step 2 : for iterate the loop on first row of the matrix and find the max area in histogram 
        step 3: now iterate the loop from 1 to n-1 and second loop from 0 to m-1 and keep adding the i-1 of j if !=0 else puth the res[i][j] =0
        step 4: once jth row completed find the max area in histogram and keep repeating
     */
    /**
     * Calculates the maximum area of rectangle in a histogram.
     * 
     * This method uses a stack to find the largest rectangle in a histogram
     * by keeping track of bar indices with increasing height.
     * 
     * @param res array representing histogram bar heights
     * @return maximum area of rectangle in the histogram
     */
    int maxAreaInHistogram(int[] res) {
        int max = 0, n = res.length;
        Stack<Integer> st = new Stack<>();

        for (int j = 0; j <= n; j++) {
            int currentHeight = (j == n) ? 0 : res[j]; // Sentinel zero at the end

            while (!st.isEmpty() && currentHeight < res[st.peek()]) {
                int height = res[st.pop()];
                int width = st.isEmpty() ? j : j - st.peek() - 1;
                max = Math.max(max, height * width);
            }
            st.push(j);
        }
        return max;
    }

    /**
     * Computes the area of the largest rectangle containing only 1s in a 2D binary matrix.
     * 
     * The idea is to treat each row of the matrix as the base of a histogram.
     * Heights of the histogram bars are calculated based on the number of consecutive 1s
     * above the current row in each column.
     * 
     * For each row, we calculate the largest area of rectangle using the histogram approach.
     * 
     * @param matrix 2D binary matrix with characters '0' or '1'
     * @return area of the largest rectangle containing only 1s
     */
    public int maximalRectangle(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return 0;

        int n = matrix.length, m = matrix[0].length;
        int[] res = new int[m]; // Histogram heights
        int max = 0;

        // Initialize the first row
        for (int i = 0; i < m; i++) {
            res[i] = matrix[0][i] - '0';
        }
        max = maxAreaInHistogram(res);

        // Iterate row by row
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < m; j++) {
                res[j] = (matrix[i][j] != '0') ? res[j] + 1 : 0;
            }
            max = Math.max(max, maxAreaInHistogram(res));
        }
        return max;
    }
}
