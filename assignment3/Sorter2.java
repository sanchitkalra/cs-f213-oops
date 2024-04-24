package assignment3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

interface SortOperator<T extends Comparable<T>> {
    public void sort(T[] arr);
}

class SortRunner<T extends Comparable<T>> {
    T[] arr;

    public SortRunner(T[] arr) {
        this.arr = arr;
    }

    public void sort(SortOperator<T> op) {
        op.sort(arr);
    }
}

class StringComparator {
    int compare(String str1, String str2) {
        int n1 = str1.length();
        int n2 = str2.length();
        int minLength = Math.min(n1, n2);
        for (int i = 0; i < minLength; i++) {
            char c1 = Character.toLowerCase(str1.charAt(i));
            char c2 = Character.toLowerCase(str2.charAt(i));
            if (c1 < c2) {
                return -1;
            } else if (c1 > c2) {
                return 1;
            }
        }
        if (n1 < n2) {
            return -1;
        } else if (n1 > n2) {
            return 1;
        } else {
            return 0;
        }
        // int len1 = s1.length();
        // int len2 = s2.length();
        // int min = Math.min(len1, len2);

        // for (int i = 0; i < min; i++) {
        // char c1 = Character.toLowerCase(s1.charAt(i));
        // char c2 = Character.toLowerCase(s2.charAt(i));

        // if (c1 != c2) {
        // return c1 - c2;
        // }
        // }

        // return len1 - len2;
    }
}

public class Sorter2 {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
            String dataType = br.readLine();
            String[] values = br.readLine().split(" ");

            if (dataType.equalsIgnoreCase("Integer")) {
                Integer[] intArr = new Integer[values.length];
                for (int i = 0; i < values.length; i++) {
                    intArr[i] = Integer.parseInt(values[i]);
                }

                SortRunner<Integer> sr = new SortRunner<Integer>(intArr);
                SortOperator<Integer> so = (arr) -> {
                    for (int i = 0; i < arr.length - 1; i++) {
                        for (int j = 0; j < arr.length - 1 - i; j++) {
                            if (arr[j] > arr[j + 1]) {
                                int temp = arr[j];
                                arr[j] = arr[j + 1];
                                arr[j + 1] = temp;
                            }
                        }
                    }
                };
                sr.sort(so);

                for (int k = 0; k < sr.arr.length - 1; k++) {
                    System.out.print(sr.arr[k] + " ");
                }
                System.out.print(sr.arr[sr.arr.length - 1]);
            } else if (dataType.equalsIgnoreCase("Double")) {
                Double[] doubleArr = new Double[values.length];
                for (int i = 0; i < values.length; i++) {
                    doubleArr[i] = Double.parseDouble(values[i]);
                }
                SortRunner<Double> sr = new SortRunner<Double>(doubleArr);
                SortOperator<Double> so = (arr) -> {
                    for (int i = 0; i < arr.length - 1; i++) {
                        for (int j = 0; j < arr.length - 1 - i; j++) {
                            if (arr[j] > arr[j + 1]) {
                                double temp = arr[j];
                                arr[j] = arr[j + 1];
                                arr[j + 1] = temp;
                            }
                        }
                    }
                };
                sr.sort(so);

                for (int k = 0; k < sr.arr.length - 1; k++) {
                    System.out.print(sr.arr[k] + " ");
                }
                System.out.print(sr.arr[sr.arr.length - 1]);
            } else {
                StringComparator sc = new StringComparator();

                SortRunner<String> sr = new SortRunner<String>(values);
                SortOperator<String> so = (arr) -> {
                    int n = arr.length;
                    for (int i = 0; i < n - 1; i++) {
                        for (int j = 0; j < n - i - 1; j++) {
                            if (sc.compare(arr[j], arr[j + 1]) > 0) {
                                String temp = arr[j];
                                arr[j] = arr[j + 1];
                                arr[j + 1] = temp;
                            }
                        }
                    }
                };
                sr.sort(so);

                for (int k = 0; k < values.length - 1; k++) {
                    System.out.print(values[k] + " ");
                }
                System.out.print(values[values.length - 1]);
            }
        } catch (IOException e) {

        }
    }
}