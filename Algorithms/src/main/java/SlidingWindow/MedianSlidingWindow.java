package SlidingWindow;

import java.util.*;

public class MedianSlidingWindow {

    public static double[] medianSlidingWindow(int[] nums, int k) {
        double[] result = new double[nums.length - k + 1];
        int start = 0;

        TreeSet<Integer> lo = new TreeSet<>((a, b) -> (nums[a] != nums[b] ? Integer.compare(nums[a], nums[b]) : a - b));
        TreeSet<Integer> hi = new TreeSet<>((a, b) -> (nums[a] != nums[b] ? Integer.compare(nums[a], nums[b]) : a - b));

        for (int i = 0; i < nums.length; i++) {
            lo.add(i);
            hi.add(lo.pollLast());
            if(hi.size()>lo.size()) lo.add(hi.pollFirst());
            if (lo.size() + hi.size() == k) {
                result[start]=lo.size()==hi.size()? nums[lo.last()]/2.0+ nums[hi.first()]/2.0: nums[lo.last()]/1.0;
                if (!lo.remove(start)) hi.remove(start);
                start++;
            }
        }
        return result;
    }

    //time limit exceed
    public static double[] medianSlidingWindowArray(int[] nums, int k) {
        double[] result = new double[nums.length - k + 1];

        int[] kWindow = new int[k];

        for (int i = 0 ; i < nums.length - k + 1; i++) {
            for (int n = 0; n < k; n++) {
                kWindow[n] = nums[i+n];
                if (n == k - 1){
                    Arrays.sort(kWindow);
                    if (k%2==0){
                        result[i] = ((double) kWindow[k/2-1] + kWindow[k/2])/2;
                    } else {
                        result[i] = kWindow[k/2];
                    }
                }
            }
        }
        return result;
    }
}
