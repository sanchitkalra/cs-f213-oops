package assignment2;

class Manager {
    private boolean arr[];

    Manager(int n) {
        arr = new boolean[n + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = true;
        }
    }

    synchronized void set(int index, boolean value) {
        arr[index] = value;
    }

    synchronized boolean get(int index) {
        return arr[index];
    }

    int getCount() {
        int count = 0;
        for (int i = 2; i < arr.length; i++) {
            if (arr[i] == true) {
                // System.out.println(i);
                count++;
            }
        }
        return count;
    }
}

class ThreadRunner implements Runnable {
    int tid;
    Thread t;
    Manager manager;
    int low;
    int high;

    ThreadRunner(int tid, Manager manager, int low, int high) {
        this.tid = tid;
        this.manager = manager;
        this.low = low;
        this.high = high;
        System.out.println("for tid " + tid + " low: " + this.low + " and high: " + this.high);
        t = new Thread(this, "thread " + this.tid);
    }

    public void run() {
        for (int i = low; i < Math.sqrt(high); i++) {
            if (manager.get(i) == true) {
                for (int j = (i * i); j < high; j = j + i) {
                    manager.set(j, false);
                }
            }
        }

        int count = 0;
        for (int i = low; i < high; i++) {
            count++;
        }
        System.out.println("count for tid " + tid + " is " + count);
    }
}

public class PrimesAssignment {
    public static void main(String[] args) {
        int nCores = Runtime.getRuntime().availableProcessors();
        System.out.println(nCores);
        int num = 500;
        int partitionSize = num / nCores;
        System.out.println(partitionSize);

        Manager m = new Manager(num);
        ThreadRunner threads[] = new ThreadRunner[nCores];

        for (int i = 0; i < nCores; i++) {
            if (i == 0) {
                threads[i] = new ThreadRunner(i, m, 2, partitionSize);
            } else if (i == nCores - 1) {
                threads[i] = new ThreadRunner(i, m, i * partitionSize, num + 1);
            } else {
                threads[i] = new ThreadRunner(i, m, i * partitionSize, (i + 1) * partitionSize);
            }
        }

        for (int i = 0; i < nCores; i++) {
            threads[i].t.start();
        }

        for (int i = 0; i < nCores; i++) {
            try {
                threads[i].t.join();
            } catch (InterruptedException e) {
                System.out.println("interrupt excp on thread " + i);
            }
        }

        System.out.println(m.getCount());
    }
}
