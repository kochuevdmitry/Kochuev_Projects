package TwoPointers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TwoSum {

    public static int[] twoSum(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        int[] indices = new int[2];
        int lo = 0, hi = nums.length - 1;

        while (lo < hi) {
            int currSum = nums[lo] + nums[hi];
            if (currSum < target || (lo > 0 && nums[lo] == nums[lo - 1])) {
                ++lo;
            } else if (currSum > target || (hi < nums.length - 1 && nums[hi] == nums[hi + 1])) {
                --hi;
            } else {
                indices[0] = lo;
                indices[1] = hi;
                //res.add(Arrays.asList(nums[lo++], nums[hi--]));
                break;
            }
        }

        return indices;
    }
}
