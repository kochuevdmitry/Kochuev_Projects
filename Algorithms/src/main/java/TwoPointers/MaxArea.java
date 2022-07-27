package TwoPointers;

public class MaxArea {

    public static int maxArea(int[] height) {
        int l = 0;
        int r = height.length - 1;
        int maxArea = 0;
        while (l < r) {
            int area = (r - l) * (Math.min(height[l], height[r]));
            maxArea = Math.max(maxArea, area);
            if (height[l] < height[r]) {
                l++;
            } else {
                r--;
            }
        }
        return maxArea;
    }
}
