package BinarySearch;

public class SearchInRotatedArray {

    //https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/
    public static int findMin(int[] nums) {
        int left = 0, right = nums.length - 1, pivot = 0;

        if (right == 1 ){
            if (nums[left] < nums[right]) return nums[left];
            else return nums[right];
        }

        while (left <= right) {
            pivot = left + (right - left) / 2;
            if (pivot == 0 ) if ( nums[left] < nums [right]) return nums[left]; else return nums[right];
            if (nums[pivot] < nums[right] && nums[pivot -1] < nums[pivot]) right = pivot - 1;
            else if(nums[pivot-1] > nums[pivot]) return nums[pivot]; else left = pivot + 1;
        }
        return nums[pivot];
    }
}
