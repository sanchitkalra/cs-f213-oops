package assignment2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

class MergeRunner implements Runnable {
    int low, high, mid, arr[];
    Thread t;

    MergeRunner(int low, int high, int mid, int arr[]) {
        this.low = low;
        this.high = high;
        this.mid = mid;
        this.arr = arr;

        t = new Thread(this);
    }

    public void run() {
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
            if (left[i] <= right[j]) {
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
}

class SortRunner implements Runnable {
    int low, high, arr[];
    Thread t;

    SortRunner(int low, int high, int arr[]) {
        this.low = low;
        this.high = high;
        this.arr = arr;
        t = new Thread(this);
    }

    public void run() {
        if (low < high) {
            int mid = low + (high - low) / 2;

            try {
                SortRunner srlow = new SortRunner(low, mid, arr);
                SortRunner srhigh = new SortRunner(mid + 1, high, arr);

                srlow.t.start();
                srhigh.t.start();

                srlow.t.join();
                srhigh.t.join();

                MergeRunner mr = new MergeRunner(low, high, mid, arr);

                mr.t.start();
                mr.t.join();
            } catch (InterruptedException e) {
                System.out.println("InterruptedException: Thread interrupted");
                return;
            }
        }
    }
}

class MergeSortAlgo {
    int arr[];

    MergeSortAlgo(int arr[]) {
        this.arr = arr;
    }

    void go() {
        try {
            SortRunner sr = new SortRunner(0, arr.length - 1, arr);
            sr.t.start();
            sr.t.join();
        } catch (InterruptedException e) {
            System.out.println("InterruptedException: Thread interrupted");
            return;
        }
    }
}

public class Sorter2 {
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

        MergeSortAlgo mSortAlgo = new MergeSortAlgo(arr);
        mSortAlgo.go();

        // for (int x : mSortAlgo.arr) {
        // System.out.print(x);
        // }

        for (int k = 0; k < mSortAlgo.arr.length - 1; k++) {
            System.out.print(mSortAlgo.arr[k] + " ");
        }
        System.out.print(mSortAlgo.arr[mSortAlgo.arr.length - 1]);

    }
}
