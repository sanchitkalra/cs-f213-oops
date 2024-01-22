public class MainOverloading {
    public static void main(int num) {
        // this is the overloaded main function that the JVM does not invoke
        System.out.println("The number was " + String.valueOf(num));
    }

    public static void main(String[] args) {
        // This is the main function the JVM will invoke, and then we can also create
        // another main funciton in this class that we can call using this main method.
        System.out.println("JVM invoked main function");
        main(10);
        System.out.println("Returned from overloaded main");
    }
}
