# Threading

- multithreaded program contains two or more parts that can run concurrently. each part is called a thread and each thread defines a seperate path of execution.
- two kinds of multitasking: threads & processes
- threads are lighter because:
    1. share same address space
    2. cooperatively share same heavy weight process
    3. inter-thread comm is in-expensive
    4. context switching is lower in cost

## Java Thread Model

- Single threaded applications use event loop + polling. single thread continuously keeping polling the event queue to decide what to do next, and no two things can happen at the same time.
- in a single threaded application, the thread may block and the entire program stops running (not ideal).
- thread can be running, ready to run, suspended, resumed, blocked
- terminated thread cannot be resumed

## Thread Priorities

- integers that specify the relative priority. used to decide when to switch from one thread to another aka context switch.
- context switch can happen for two reasons:
    1. A thread can voluntarily relinquish control. thread yeilds, sleeps, or gets blocked. highest priority thread gets CPU
    2. A thread can be preempted by a higher-priority thread. 

## Thread Class & Runnable Interface

- threads can be created by implementing the `Runnable` interface or extending `Thread` class.
- Main Thread
    - Java program startup => main thread starts immediately.
    - all thread spawned from main thread
    - last thread to finish execution -> performs various shutdown actions
- we can get a reference to the current thread by calling the public static method `currentThread()`
- a thread object contains information about:
    - the name of the thread
    - the priority of a the thread (default is 5)
    - the group the thread belongs to. a thread group is a data structure that controls the state of collection of threads as a whole, for example, we can start or stop a group of thread together using a thread group
- threads can be put to sleep for a specified amount of milliseconds using the sleep function, and it may throw an InterruptedException exception if the another thread interrupts the sleeping thread
- setName(getName) to set(get) the name of the thread 

## Creating a thread - Runnable

- implement the Runnable interface
- min requirement -> implement `run()` function
- run is the function that contains the code for the new thread
- thread ends when run returns 
- steps to create thread
    - create a class that implements the Runnable interface
    - create an object of type Thread using the constructor: `Thread(Runnable threadOb, String threadName)` inside the class that implements `Runnable`. this call creates the new thread which is currently not running
    - start the thread with a call to the start method which in turns calls the run method we implemented 
- see [example](/class-code/ThreadingBasicWithRunnable.java)
- this is generally seen as the preferred way because conventionally when we're extending, we're enhancing or adapting another class, but this is not the case when we're creating a thread by extending the Thread class. Generally we should only extend the Thread class when we're modifying it's methods beyond run.

## Creating a thread - Extending Thread

- create new class that extends Thread & overrides run method -> create an instance of that class -> call start to begin execution
- see [example](/class-code/ThreadingBasicWithThread.java)

## Creating multiple Threads

- create multiple thread by instantiating the runnable interface's implementing class multiple times.
- see [example](/class-code/MultipleThreads.java)

## isAlive & join

- to determine if a thread has finished executing, we can call `final boolean isAlive()` on the thread.
- calling thread wait for a thread to finish by calling `final void join() throws InterruptedException` on that thread
- see [example](/class-code/ThreadingWithJoin.java)

## Thread Priorities

- used by thread scheduler to decide when each thread should be run.
- higher priority thread => gets allocated relatively more CPU time.
- set priority with the `final void setPriority(int level)` method. level must be with 1 (MIN_PRIORITY) and 10 (MAX_PRIORITY) (both incluisve). to set to default pass NORM_PRIORITY (5). these priorities are defined as static final variables in Thread class.
- get priority level with `final int getPriority()`
- _The safest way to obtain predictable, cross-platform behavior with Java is to use threads that voluntarily give up control of the CPU._

## Syncronisation

- ensure a shared resource will be used by only one thread at a time
- ensured via monitor (essentially acting like a mutually exclusive lock)
- only one thread can own a monitor at a given time
- acquired lock => entered the monitor
- other threads (threads waiting for the monitor) attempting to enter the locked monitor will be suspended until the first thread exits the monitor

### Synchronized Methods

- all objects have their implicit monitor associated with them.
- enter an object's monitor => call a method that has been modified with the `synchronized` keyword.
- all other threads that try to call it (or any other synchronized method) on the same instance have to wait.
- to exit the lock, the monitor simply returns from the synchronized method.
```java
// example:
synchronized void call(String msg) {
    System.out.print("[" + msg);
    
    try {
        Thread.sleep(1000);
    } catch(InterruptedException e) {
        System.out.println("Interrupted");
    }
    
    System.out.println("]");
}
```

### Synchronized Statements

- to add sync to code that does not used method modified with the `synchronized` keyword, we can put the method calls inside a `synchronized` block.
```java
synchronized (objRef) { // objRef is a reference to the object to be synchronized
    // A synchronized block ensures that a call to a synchronized method that is a member of objRef’s class occurs only after the current thread has successfully entered objRef’s monitor.
    // statements to by synchronized
}
```

```java
// example
// say the method in the previous section was not implemented with the synchronized keyword, we could still use it in a sync fashion as follows:
synchronized(target) { // synchronized block
    target.call(msg); // here target is the obj of the class containing the call method
}
```

## Interthread Communication

- key methods:
    - `wait()`: calling thread gives up the monitor and goes to sleep until some other thread enters the same monitor and calls `notify()` or `notifyAll()`. this fn may throw the InterruptedExpection. optionally a period of time to wait can be specified
    - `notify()`: wake up a thread which called wait on a particular object
    - `notifyAll()`: wake up all the threads which called wait on a particular object. one of these threads will be granted access to the monitor
- these methods described above are implemented as final in Object so all classes have access to them.
- Although wait() normally waits until notify() or notifyAll() is called, there is a possibility that in very rare cases the waiting thread could be awakened due to a spurious wakeup. In this case, a waiting thread resumes without notify() or notifyAll() having been called. (In essence, the thread resumes for no apparent reason.) Because of this remote possibility, the Java API documentation recommends that calls to wait() should take place within a loop that checks the condition on which the thread is waiting.

## Deadlocks

- circular dependency on a pair of syncronized objects
