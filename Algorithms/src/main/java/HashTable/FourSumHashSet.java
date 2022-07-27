package HashTable;

import java.util.*;

public class FourSumHashSet {


   /* Time Complexity: O(n^{k - 1}), or O(n^3) for 4Sum.
   We have k - 2 loops iterating over n elements, and twoSum is O(n).
    Note that for k > 2, sorting the array does not change the overall time complexity.
    Space Complexity: O(n) for the hash set. The space needed for the recursion will not exceed O(n).*/

    //https://leetcode.com/problems/4sum/
    public static List<List<Integer>> fourSum(int[] nums, int target, int k) {
        Arrays.sort(nums);
        return kSum(nums, target, 0, k);
    }

    public static List<List<Integer>> kSum(int[] nums, long target, int start, int k) {
        List<List<Integer>> res = new ArrayList<>();

        // If we have run out of numbers to add, return res.
        if (start == nums.length) {
            return res;
        }

        // There are k remaining values to add to the sum. The
        // average of these values is at least target / k.
        long average_value = target / k;

        // We cannot obtain a sum of target if the smallest value
        // in nums is greater than target / k or if the largest
        // value in nums is smaller than target / k.
        if  (nums[start] > average_value || average_value > nums[nums.length - 1]) {
            return res;
        }

        if (k == 2) {
            return twoSum(nums, target, start);
        }

        for (int i = start; i < nums.length; ++i) {
            if (i == start || nums[i - 1] != nums[i]) {
                for (List<Integer> subset : kSum(nums, target - nums[i], i + 1, k - 1)) {
                    res.add(new ArrayList<>(Arrays.asList(nums[i])));
                    res.get(res.size() - 1).addAll(subset);
                }
            }
        }

        return res;
    }

    public static List<List<Integer>> twoSum(int[] nums, long target, int start) {
        List<List<Integer>> res = new ArrayList<>();
        Set<Long> s = new HashSet<>();

        for (int i = start; i < nums.length; ++i) {
            if (res.isEmpty() || res.get(res.size() - 1).get(1) != nums[i]) {
                if (s.contains(target - nums[i])) {
                    res.add(Arrays.asList((int)target - nums[i], nums[i]));
                }
            }
            s.add((long)nums[i]);
        }

        return res;
    }
}
