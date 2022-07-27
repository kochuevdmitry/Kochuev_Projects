package HashTable;

import java.util.Arrays;
import java.util.HashSet;

public class SingleNumberHash {

    //https://leetcode.com/problems/single-number/
    public static int singleNumberHash(int[] nums) {
        HashSet<Integer> singleSet = new HashSet<>();

        //O(N)
        for (int i = 1; i < nums.length - 1; i++) {
            if (singleSet.contains(nums[i])) {
                singleSet.remove(nums[i]);
            } else {
                singleSet.add(nums[i]);
            }
        }
        return (int) singleSet.toArray()[0];
    }
}
