import java.util.Arrays;
import java.util.Scanner;

class Solution {
    private int[] nums;
    private int[] sums;

    public boolean divide(int[] nums) {
        this.nums = nums;

        int length = nums.length;

        if (length == 1)
            return false;

        Arrays.sort(nums);

        sums = new int[length + 1];

        for (int i = 0; i < length; i++)
            sums[i + 1] = sums[i] + nums[i];

        int sum = sums[length];

        for (int i = 1, stop = length / 2; i <= stop; i++)
            if ((sum * i) % length == 0 && findSum(i, length, (sum * i) / length))
                return true;

        return false;
    }

    private boolean findSum(int k, int pos, int target) {
        if (k == 1) {
            while (true) {
                if (nums[--pos] <= target) {
                    break;
                }
            }
            return nums[pos] == target;
        }
        for (int i = pos; sums[i] - sums[i-- - k] >= target;) {
            if (sums[k - 1] <= target - nums[i] && findSum(k - 1, i, target - nums[i])) {
                return true;
            }
        }
        return false;
    }
}

public class Main {
    public static void main(String[] args) {
        // Take input for integer array nums
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt(); // first integer is the size of the array
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }
        scanner.close();

        Solution solution = new Solution();
        boolean result = solution.divide(nums);
        System.out.println(result);
    }
}