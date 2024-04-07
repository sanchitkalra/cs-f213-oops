package assignment2;

import java.util.ArrayList;
import java.util.Scanner;

class PrimeManager {
    boolean arr[];
    int num;

    PrimeManager(int num) {
        this.num = num + 1;
        arr = new boolean[num + 1];
        for (int k = 0; k < arr.length; k++)
            arr[k] = true;
    }

    long getSum() {
        long sum = 0;
        for (int i = 2; i < arr.length; i++) {
            if (arr[i] == true) {
                // System.out.println(i);
                sum += i;
            }
        }
        return sum;
    }

    long getCount() {
        long sum = 0;
        for (int i = 2; i < arr.length; i++) {
            if (arr[i] == true) {
                sum += 1;
            }
        }
        return sum;
    }
}

class PrimeRunner implements Runnable {
    int tid;
    Thread t;
    int number;
    PrimeManager primeManager;
    long sum = 0;

    PrimeRunner(int number, int tid, PrimeManager primeManager) {
        this.number = number;
        t = new Thread(this, "thread " + tid);
        this.primeManager = primeManager;
    }

    public void run() {
        for (int j = (this.number * this.number); j < primeManager.num; j = j + number) {
            if (j % 3 == 0 || j % 5 == 0 || j % 7 == 0)
                if (primeManager.arr[j] == true)
                    sum += j;
            primeManager.arr[j] = false;
        }
    }
}

public class Primes {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        sc.close();

        PrimeManager primeManager = new PrimeManager(num);

        ArrayList<PrimeRunner> pr = new ArrayList<PrimeRunner>();

        for (int i = 2; i < Math.sqrt(primeManager.num); i++) {
            if (primeManager.arr[i] == true) {
                pr.add(new PrimeRunner(i, i, primeManager));
                pr.get(pr.size() - 1).t.start();
            }
        }

        for (PrimeRunner p : pr) {
            try {
                p.t.join();
            } catch (InterruptedException e) {
                System.out.println("InterruptedException: Thread interrupted");
            }
        }

        long sum = 0;

        // for (PrimeRunner p : pr) {
        // sum += p.sum;
        // }

        // for (int k = 3; k < Math.min(num, 9); k += 3) {
        // if (primeManager.arr[k] == true)
        // sum += k;
        // }
        // for (int k = 5; k < Math.min(num, 25); k += 5) {
        // if (primeManager.arr[k] == true)
        // sum += k;
        // }
        // for (int k = 7; k < Math.min(num, 49); k += 7) {
        // if (primeManager.arr[k] == true)
        // sum += k;
        // }

        for (long i = 1; i <= num; i++) {
            if (i % 3 == 0 || i % 5 == 0 || i % 7 == 0) {
                sum += i;
            }
        }

        System.out.print(primeManager.getSum() + " " + sum);
    }
}
