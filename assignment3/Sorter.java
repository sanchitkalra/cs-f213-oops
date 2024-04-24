package assignment3;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

interface SortCompInterface<T> {
    void op(T arr[]);
}

class SortRunner<T extends Comparable<T>> {
    T arr[];
    int size;

    SortRunner(T arr[]) {
        this.arr = arr;
        this.size = arr.length;
    }

    void merge(int low, int high, int mid) {
        int v1 = mid - low + 1;
        int v2 = high - mid;

        T left[] = Arrays.copyOfRange(arr, low, mid);
        T right[] = Arrays.copyOfRange(arr, mid + 1, high);

        // for (int i = 0; i < v1; i++)
        // left[i] = arr[low + i];
        // for (int i = 0; i < v2; i++)
        // right[i] = arr[mid + 1 + i];

        int i = 0, j = 0;
        int k = low;

        while (i < v1 && j < v2) {
            if (left[i].compareTo(right[j]) < 0) {
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

public class Sorter {
    static <T> T[] takeInput(String line, Class<T> type) {
        String[] lineElem = line.split(" ");

        ArrayList<T> list = new ArrayList<T>();

        for (String elem : lineElem) {
            try {
                list.add(type.cast(elem));
            } catch (NumberFormatException e) {

            }
        }

        T[] array = (T[]) Array.newInstance(type, list.size());

        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }

        return array;
    }

    public static void main(String[] args) {
        try (FileInputStream fis = new FileInputStream(args[0])) {

            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            String type = reader.readLine();
            String line = reader.readLine();

            switch (type) {
                case "Integer":
                    Integer intArr[] = takeInput(line, Integer.class);

                    SortRunner<Integer> intSort = new SortRunner<Integer>(intArr);

                    SortCompInterface<Integer> fn1 = (arr) -> {

                    };

                    intSort.sort(0, intArr.length);

                    for (int k = 0; k < intArr.length - 1; k++) {
                        System.out.print(intArr[k] + " ");
                    }

                    System.out.print(intArr[intArr.length - 1]);
                    break;

                case "String":
                    String strArr[] = takeInput(line, String.class);
                    SortRunner<String> strSort = new SortRunner<String>(strArr);
                    strSort.sort(0, strArr.length);
                    for (int k = 0; k < strArr.length - 1; k++) {
                        System.out.print(strArr[k] + " ");
                    }
                    System.out.print(strArr[strArr.length - 1]);
                    break;

                case "Double":
                    Double doubleArr[] = takeInput(line, Double.class);
                    SortRunner<Double> doubleSort = new SortRunner<Double>(doubleArr);
                    doubleSort.sort(0, doubleArr.length);
                    for (int k = 0; k < doubleArr.length - 1; k++) {
                        System.out.print(doubleArr[k] + " ");
                    }
                    System.out.print(doubleArr[doubleArr.length - 1]);
                    break;

                default:
                    break;
            }

        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException: Error opening the specified file");
            return;
        } catch (IOException e) {
            System.out.println("IOException: Error in IO");
            return;
        }
    }
}
