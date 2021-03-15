package filechannel;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2021/1/30 2:31
 */
public class Solution {
    public int[] searchRange(int[] nums, int target) {
        int[] res = {-1, -1};
        if (nums == null || nums.length == 0) {
            return res;
        }

        res[0] = findFirstTarget(nums, target);
        res[1] = findLastTarget(nums, target);
        return res;
    }

    private int findLastTarget(int[] nums, int target) {
        int ans = -1;
        int l = 0;
        int r = nums.length - 1;
        while (l < r) {
            int mid = (r + l) / 2;
            //[mid + 1, r]
            if (nums[mid] < target) {
                l = mid + 1;
            } else if( nums[mid] > target) {
                r = mid - 1;
            } else {
                l = mid;
            }
        }
        if(nums[r] == target) {
            return r;
        }
        return ans;
    }

    private int findFirstTarget(int[] nums, int target) {
        int ans = -1;
        int l = 0;
        int r = nums.length - 1;
        while (l < r) {
            int mid = (l + r) / 2;
            if (nums[mid] < target) {
                l = mid + 1;
            } else if (nums[mid] > target) {
                r = mid - 1;
            } else {
                r = mid;
            }
        }
        if (nums[l] == target) {
            return l;
        }
        return ans;
    }

}
