package assignment2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

class MatrixRunner implements Runnable {
    int rowId, size;
    long tMatrix[][], matrix[][][];
    Thread t;

    MatrixRunner(long tid, int size, int rowId, long matrix[][][], long tMatrix[][]) {
        t = new Thread(this, "thread " + tid);
        this.size = size;
        this.rowId = rowId;
        this.matrix = matrix;
        this.tMatrix = tMatrix;
    }

    public void run() {
        for (int j = 0; j < this.size; j++) {
            for (int k = 0; k < this.size; k++) {
                tMatrix[rowId][j] += matrix[0][rowId][k] * matrix[1][k][j];
            }
        }
    }
}

class MatrixMultiplication {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("File not specified");
            return;
        }

        int size = 0;
        int ptr = -1;

        long arr[][][] = new long[3][size][size];

        try (FileInputStream fis = new FileInputStream(args[0])) {

            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            while (reader.ready()) {
                String line = reader.readLine();
                String[] lineElem = line.split(" ");
                if (ptr == -1) {
                    // size is yet to be read
                    try {
                        size = Integer.parseInt(lineElem[0]);
                        arr = new long[3][size][size];
                    } catch (NumberFormatException e) {
                        System.out.println("NumberFormatException: Error in string to number conversion");
                    }
                } else {
                    int k = 0;
                    for (int i = 0; i < size; i++) {
                        for (int j = 0; j < size; j++) {
                            try {
                                arr[ptr][i][j] = Integer.parseInt(lineElem[k]);
                            } catch (NumberFormatException e) {
                                System.out.println("NumberFormatException: Error in string to number conversion");
                            }
                            k++;
                        }
                    }
                }
                ptr++;
            }

        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException: Error opening the specified file");
            return;
        } catch (IOException e) {
            System.out.println("IOException: Error in IO");
            return;
        }

        long tMatrix[][] = new long[size][size];

        MatrixRunner threads[] = new MatrixRunner[size];
        for (int k = 0; k < size; k++) {
            threads[k] = new MatrixRunner(k, size, k, arr, tMatrix);
            threads[k].t.start();
        }

        for (int k = 0; k < size; k++) {
            try {
                threads[k].t.join();
            } catch (InterruptedException e) {
                System.out.println("InterruptedException: Thread interrupted");
            }
        }

        arr[0] = tMatrix;
        arr[1] = arr[2];

        MatrixRunner threads2[] = new MatrixRunner[size];
        long tMatrix2[][] = new long[size][size];

        for (int k = 0; k < size; k++) {
            threads2[k] = new MatrixRunner(k, size, k, arr, tMatrix2);
            threads2[k].t.start();
        }

        for (int k = 0; k < size; k++) {
            try {
                threads2[k].t.join();
            } catch (InterruptedException e) {
                System.out.println("InterruptedException: Thread interrupted");
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(tMatrix2[i][j] + (i == size - 1 && j == size - 1 ? "" : " "));
            }
        }
    }
}