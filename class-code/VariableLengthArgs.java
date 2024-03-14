class Vars {
    void method1() {
        System.out.println("no params");
    }

    void method1(int... v) {
        System.out.println("variable length params");
        for (int x : v) {
            System.out.println(x);
        }
    }

    void method1(String name, int... v) {
        System.out.println("normal + variable length params");
        System.out.println(name);
        for (int x : v) {
            System.out.println(x);
        }
    }
}

public class VariableLengthArgs {
    public static void main(String[] args) {
        Vars v = new Vars();
        v.method1();
        v.method1(1, 2, 3, 4, 4);
        v.method1("Sanchit", 2, 3, 4, 4);
    }
}