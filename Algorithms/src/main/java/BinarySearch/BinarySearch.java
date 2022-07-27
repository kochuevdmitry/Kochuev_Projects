package BinarySearch;

public class BinarySearch {

    /*Recursion comes with overhead of saving current state. Hence, It's slower.
    Recursion uses internal Stack space.
    So, Space Complexity of Recursive Approach is O(Log(n)) whereas Iterative is O(1)
    If the array length is very large, Recursive program can crash due to Stack Overflow Error.
    Time O(LogN)*/

    //https://leetcode.com/problems/binary-search/
    public static int binarySearchIntArray(int[] nums, int target) {
        int pivot, left = 0, right = nums.length - 1;

        while (left <= right) {
            pivot = left + (right - left) / 2;
            if (nums[pivot] == target) return pivot;
            if (target < nums[pivot]) right = pivot - 1;
            else left = pivot + 1;
        }
        return -1;
    }

}
