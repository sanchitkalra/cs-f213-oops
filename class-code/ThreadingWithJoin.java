// Using join() to wait for threads to finish.
class NewThreadWithJoin implements Runnable {
    String name; // name of thread
    Thread t;

    NewThreadWithJoin(String threadname) {
        name = threadname;
        t = new Thread(this, name);
        System.out.println("New thread: " + t);
    }

    // This is the entry point for thread.
    public void run() {
        try {
            for (int i = 5; i > 0; i--) {
                System.out.println(name + ": " + i);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.out.println(name + " interrupted.");
        }
        System.out.println(name + " exiting.");
    }
}

public class ThreadingWithJoin {
    public static void main(String[] args) {
        NewThreadWithJoin nt1 = new NewThreadWithJoin("One");
        NewThreadWithJoin nt2 = new NewThreadWithJoin("Two");
        NewThreadWithJoin nt3 = new NewThreadWithJoin("Three");
        // Start the threads.
        nt1.t.start();
        nt2.t.start();
        nt3.t.start();
        System.out.println("Thread One is alive: "
                + nt1.t.isAlive());
        System.out.println("Thread Two is alive: "
                + nt2.t.isAlive());
        System.out.println("Thread Three is alive: "
                + nt3.t.isAlive());
        // wait for threads to finish
        try {
            System.out.println("Waiting for threads to finish.");
            nt1.t.join();
            nt2.t.join();
            nt3.t.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread Interrupted");
        }
        System.out.println("Thread One is alive: "
                + nt1.t.isAlive());
        System.out.println("Thread Two is alive: "
                + nt2.t.isAlive());
        System.out.println("Thread Three is alive: "
                + nt3.t.isAlive());
        System.out.println("Main thread exiting.");
    }
}