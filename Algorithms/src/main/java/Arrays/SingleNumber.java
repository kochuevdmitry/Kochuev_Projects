package Arrays;

import java.util.Arrays;
import java.util.HashSet;

public class SingleNumber {

    //https://leetcode.com/problems/single-number/
    public static int singleNumber(int[] nums) {
        Arrays.sort(nums);//O(logN)

        //O(N)
        for (int i = 0; i < nums.length - 1; i = i + 2) {
            if (i + 1 <= nums.length - 1) {
                if (nums[i] != nums[i + 1]) {
                    return nums[i];
                }
            } else return nums[i];
        }
        return nums[nums.length - 1];
    }

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

    public static int singleNumberXOR(int[] nums) {
        //O(N)
        int result = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            result ^= nums[i];
        }
        return result;
    }

}
