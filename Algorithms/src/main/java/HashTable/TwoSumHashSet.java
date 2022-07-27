package HashTable;

import java.util.*;

public class TwoSumHashSet {

    //time and space O(2n)
    public static int[] twoSumIndices(int[] nums, int target) {
        int[] indices = new int[2];
        Set<Integer> s = new HashSet<>();
        for (int i = 0; i < nums.length; ++i) {
                if (s.contains(target - nums[i])) {
                    for(int c = 0; c < i; c++){
                        if (nums[c] == target - nums[i]) {
                            indices[0] = c;
                            indices[1] = i;
                            return indices;
                        }
                    }
                }
            s.add(nums[i]);
        }
        return indices;
    }

    private static List<List<Integer>> twoSumHashValues(int[] nums, int target, int start) {
        List<List<Integer>> res = new ArrayList<>();
        Set<Long> s = new HashSet<>();
        for (int i = start; i < nums.length; ++i) {
            if (res.isEmpty() || res.get(res.size() - 1).get(1) != nums[i]) {
                if (s.contains(target - nums[i])) {
                    res.add(Arrays.asList((int)target - nums[i], nums[i]));
                }            }
            s.add((long)nums[i]);
        }
        return res;
    }
}
