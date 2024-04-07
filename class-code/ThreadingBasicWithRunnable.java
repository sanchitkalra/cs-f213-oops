class NewThread implements Runnable {
    Thread t;

    NewThread() {
        t = new Thread(this, "New Thread"); // new thread created, not running yet
        System.out.println("Child Thread: " + t);
    }

    public void run() {
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println("Child thread + " + i);
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            System.out.println("Child interrupted");
        }
        System.out.println("Exiting child thread");
    }
}

public class ThreadingBasicWithRunnable {
    public static void main(String[] args) {
        NewThread nt = new NewThread(); // create a new thread
        nt.t.start(); // Start the thread, run fn is called

        try {
            for (int i = 5; i > 0; i--) {
                System.out.println("Main Thread: " + i);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted.");
        }
        System.out.println("Main thread exiting.");
    }
}
