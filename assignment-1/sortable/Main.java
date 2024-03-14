import java.util.Scanner;

interface Sortable {
    void sort(int[] nums);
}

class QuickSort implements Sortable {

    private void swap(int nums[], int v1, int v2) {
        int temp = nums[v1];
        nums[v1] = nums[v2];
        nums[v2] = temp;
    }

    private int part(int[] nums, int low, int high) {
        int pivot = nums[high];

        int v1 = low - 1;

        for (int k = low; k <= high - 1; k++) {
            if (nums[k] > pivot) {
                v1++;
                // swap
                swap(nums, v1, k);
            }
        }
        swap(nums, v1 + 1, high);

        return v1 + 1;
    }

    private void sorter(int[] nums, int low, int high) {
        if (low < high) {
            int partitionPoint = part(nums, low, high);

            sorter(nums, low, partitionPoint - 1);
            sorter(nums, partitionPoint + 1, high);
        }
    }

    public void sort(int[] nums) {
        sorter(nums, 0, nums.length - 1);
    }
}

class MergeSort implements Sortable {

    void merge(int nums[], int left, int mid, int right) {
        int s1 = mid - left + 1;
        int s2 = right - mid;

        int t1[] = new int[s1];
        int t2[] = new int[s2];

        for (int k = 0; k < s1; k++)
            t1[k] = nums[left + k];
        for (int k = 0; k < s2; k++)
            t2[k] = nums[mid + 1 + k];

        int v1 = 0, v2 = 0;

        int k = left;
        while (v1 < s1 && v2 < s2) {
            if (t1[v1] >= t2[v2]) {
                nums[k] = t1[v1];
                v1++;
            } else {
                nums[k] = t2[v2];
                v2++;
            }
            k++;
        }

        while (v1 < s1) {
            nums[k] = t1[v1];
            v1++;
            k++;
        }

        while (v2 < s2) {
            nums[k] = t2[v2];
            v2++;
            k++;
        }
    }

    void sorter(int nums[], int left, int right) {
        if (left < right) {

            int mid = left + (right - left) / 2;

            sorter(nums, left, mid);
            sorter(nums, mid + 1, right);

            merge(nums, left, mid, right);
        }
    }

    public void sort(int[] nums) {
        sorter(nums, 0, nums.length - 1);
    }

}

public class Main {
    public static void main(String[] args) {
        // Take input for integer array nums
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt(); // size of array
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }
        scanner.close();

        // Create an instance of quickSort and perform sorting
        Sortable quickSort = new QuickSort();
        quickSort.sort(nums);

        // Print the sorted array using Quick Sort
        printArray(nums);

        // Create an instance of MergeSort
        Sortable mergeSort = new MergeSort();
        mergeSort.sort(nums);

        // Print the sorted array using Merge Sort
        printArray(nums);
    }

    // Method to print the elements of an array
    private static void printArray(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}