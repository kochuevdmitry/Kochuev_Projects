package SlidingWindow;

public class SlidingWindowMaximum {

    //сделаем пару массивов, в первом будет по увеличению идти в рамках окна с начала массива,
    // а второй будет по также максить по окнам но прогон справа налево
    public static int[] slidingWindowMax(int[] nums, int k) {
        int[] max_left = new int[nums.length];
        int[] max_right = new int[nums.length];

        max_left[0] = nums[0];
        max_right[nums.length - 1] = nums[nums.length - 1];

        for (int i = 1; i < nums.length; i++) {
            max_left[i] = (i % k == 0) ? nums[i] : Math.max(max_left[i - 1], nums[i]);

            int j = nums.length - i - 1;
            max_right[j] = (j % k == 0) ? nums[j] : Math.max(max_right[j + 1], nums[j]);
        }

        int[] sliding_max = new int[nums.length - k + 1];
        for (int i = 0, j = 0; i + k <= nums.length; i++) {
            sliding_max[j++] = Math.max(max_right[i], max_left[i + k - 1]);
        }
        return sliding_max;
    }
}
