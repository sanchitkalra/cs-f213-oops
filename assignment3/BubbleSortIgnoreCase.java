package assignment3;

public class BubbleSortIgnoreCase {
    public static void bubbleSortIgnoreCase(String[] arr) {
        int n = arr.length;
        boolean swapped;

        do {
            swapped = false;
            for (int i = 0; i < n - 1; i++) {
                if (compareToIgnoreCase(arr[i], arr[i + 1]) > 0) {
                    // Swap arr[i] and arr[i+1]
                    String temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    swapped = true;
                }
            }
            n--;
        } while (swapped);
    }

    public static int compareToIgnoreCase(String str1, String str2) {
        int len1 = str1.length();
        int len2 = str2.length();
        int minLen = Math.min(len1, len2);

        for (int i = 0; i < minLen; i++) {
            char c1 = Character.toLowerCase(str1.charAt(i));
            char c2 = Character.toLowerCase(str2.charAt(i));

            if (c1 != c2) {
                return c1 - c2;
            }
        }

        return len1 - len2;
    }

    public static void main(String[] args) {
        String[] arr = { "BITS", "Hyderabad", "pilani", "dubai", "goa", "mumbai", "OOP" };

        System.out.println("Before sorting:");
        printArray(arr);

        bubbleSortIgnoreCase(arr);

        System.out.println("\nAfter sorting:");
        printArray(arr);
    }

    public static void printArray(String[] arr) {
        for (String str : arr) {
            System.out.print(str + " ");
        }
        System.out.println();
    }
}
