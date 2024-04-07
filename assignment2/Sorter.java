package assignment2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

class MergeSort {
    int arr[];

    MergeSort(int arr[]) {
        this.arr = arr;
    }

    void merge(int low, int high, int mid) {
        int v1 = mid - low + 1;
        int v2 = high - mid;

        int left[] = new int[v1];
        int right[] = new int[v2];

        for (int i = 0; i < v1; i++)
            left[i] = arr[low + i];
        for (int i = 0; i < v2; i++)
            right[i] = arr[mid + 1 + i];

        int i = 0, j = 0;
        int k = low;

        while (i < v1 && j < v2) {
            if (left[i] <= right[i]) {
                arr[k] = left[i];
                i++;
                k++;
            } else {
                arr[k] = right[j];
                j++;
                k++;
            }
        }

        while (i < v1) {
            arr[k] = left[i];
            i++;
            k++;
        }

        while (j < v2) {
            arr[k] = right[j];
            j++;
            k++;
        }
    }

    void sort(int low, int high) {
        if (low < high) {
            int mid = low + (high - low) / 2;

            sort(low, mid);
            sort(mid + 1, high);

            merge(low, high, mid);
        }
    }
}

class SorterRunner implements Runnable {
    int low, high;
    int arr[];
    int tid;
    Thread t;

    SorterRunner(int tid, int low, int high, int arr[]) {
        this.low = low;
        this.high = high;
        this.arr = arr;

        this.tid = tid;
        t = new Thread(this, "thread " + tid);
    }

    public void run() {
        MergeSort ms = new MergeSort(this.arr);
        ms.sort(this.low, this.high);
    }
}

public class Sorter {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("File not specified");
            return;
        }

        int arr[] = new int[0];

        try (FileInputStream fis = new FileInputStream(args[0])) {

            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            while (reader.ready()) {
                String line = reader.readLine();
                String[] lineElem = line.split(" ");

                ArrayList<Integer> list = new ArrayList<Integer>();

                for (String elem : lineElem) {
                    try {
                        list.add(Integer.parseInt(elem));
                    } catch (NumberFormatException e) {

                    }
                }

                arr = new int[list.size()];
                arr = list.stream().mapToInt(i -> i).toArray();
            }

        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException: Error opening the specified file");
            return;
        } catch (IOException e) {
            System.out.println("IOException: Error in IO");
            return;
        }

        int cores = Runtime.getRuntime().availableProcessors();
        int count = arr.length;

        int partitionSize = count / cores;

    }
}
