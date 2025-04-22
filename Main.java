public class Main {
    public static void main(String[] args){
        System.out.println("my first program");
        System.out.println("my first program");
        Solution sol= new Solution();
        int arr[]={ 1,2,3,4,5};
        int k=6;
        System.out.println(sol.countSubsequenceWithTargetSum(arr, k));

        int arr2[]={ 1,2,3,4,5};
        int k2=9;
        System.out.println(sol.countSubsequenceWithTargetSum(arr2, k2));
    }
}
