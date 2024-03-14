abstract class SuperClass {
    abstract void abstractFn();
}

class SubClass extends SuperClass {
    void abstractFn() {
        System.out.println("abstraction defined!");
    }
}

public class Abstraction {
    public static void main(String[] args) {
        SubClass sc = new SubClass();
        sc.abstractFn();
        SuperClass ss = new SubClass();
        ss.abstractFn();
    }
}