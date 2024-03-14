class Person {
    String name; // this is default access, making it private makes it inaccessible to it's
                 // descendants

    void printName() {
        System.out.println(name);
    }
}

class Student extends Person {
    int gpa;

    void printName() {
        System.out.println("the name is: " + name);
    }

    void show() {
        System.out.println(name + " has " + gpa + " GPA");
    }
}

public class Inherit {
    public static void main(String[] args) {
        Student s = new Student();
        s.name = "Sanchit"; // can assign super class' members too as long as they
        // are default or public
        s.gpa = 10;
        s.show();
        s.printName();

        Person p = new Student();
        p.name = "Sanchit";
        p.printName(); // even if the type definition is of the superclass but the object is of type
                       // subclass, the overridden functions of the subclass will only be called, and
                       // overriding works normally.
        // p.show(); // this call on the other hand does not work because only members
        // of the reference type can be accessed, so members defined in the subclass
        // become inaccessible.

    }
}
