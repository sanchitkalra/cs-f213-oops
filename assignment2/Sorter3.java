package assignment2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Sorter3 {

    static final int MAX_THREADS = 4; // Set the maximum number of threads

    static int activeThreads = 0;

    static void merge(int[] arr, int low, int mid, int high) {
        int n1 = mid - low + 1;
        int n2 = high - mid;

        int[] left = new int[n1];
        int[] right = new int[n2];

        // Copy data to temporary arrays left[] and right[]
        for (int i = 0; i < n1; ++i)
            left[i] = arr[low + i];
        for (int j = 0; j < n2; ++j)
            right[j] = arr[mid + 1 + j];

        // Merge the temporary arrays
        int i = 0, j = 0;
        int k = low;
        while (i < n1 && j < n2) {
            if (left[i] <= right[j]) {
                arr[k] = left[i];
                i++;
            } else {
                arr[k] = right[j];
                j++;
            }
            k++;
        }

        // Copy remaining elements of left[] if any
        while (i < n1) {
            arr[k] = left[i];
            i++;
            k++;
        }

        // Copy remaining elements of right[] if any
        while (j < n2) {
            arr[k] = right[j];
            j++;
            k++;
        }
    }

    static void mergeSort(int[] arr, int low, int high) {
        if (low < high) {
            int mid = (low + high) / 2;
            if (activeThreads < MAX_THREADS) {
                activeThreads += 2;
                mergeSort(arr, low, mid);
                mergeSort(arr, mid + 1, high);
                activeThreads -= 2;
            } else {
                mergeSort(arr, low, mid);
                mergeSort(arr, mid + 1, high);
            }
            merge(arr, low, mid, high);
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("File not specified");
            return;
        }

        int[] arr;

        try (FileInputStream fis = new FileInputStream(args[0])) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line = reader.readLine();
            String[] lineElem = line.split(" ");

            ArrayList<Integer> list = new ArrayList<>();

            for (String elem : lineElem) {
                try {
                    list.add(Integer.parseInt(elem));
                } catch (NumberFormatException e) {
                    // Ignore non-integer elements
                }
            }

            arr = list.stream().mapToInt(i -> i).toArray();
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException: Error opening the specified file");
            return;
        } catch (IOException e) {
            System.out.println("IOException: Error in IO");
            return;
        }

        mergeSort(arr, 0, arr.length - 1);

        for (int x : arr) {
            System.out.print(x + " ");
        }
        System.out.println();
    }
}
