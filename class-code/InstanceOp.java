class A {
}

class B extends A {
}

class C extends B {
}

public class InstanceOp {
    public static void main(String[] args) {
        A a = new A();
        B b = new B();
        C c = new C();

        if (a instanceof A) {
            System.out.println("a instanceof A");
        }

        if (b instanceof B) {
            System.out.println("b instanceof B");
        }

        if (c instanceof C) {
            System.out.println("c instanceof C");
        }

        if (b instanceof A) {
            System.out.println("b instanceof A");
        }

        if (c instanceof A) {
            System.out.println("c instanceof A");
        }

        if (c instanceof B) {
            System.out.println("c instanceof B");
        }

        A ob;

        ob = c;

        if (ob instanceof A) {
            System.out.println("ob instanceof A");
        }
    }
}
