# Inheitance

- create a class with a set of traits common to a set of related items
- inherited class -> superclass. inheriting class -> subclass
- _subclass is a specialised version of the superclass_
- done using the `extends` keyword
```java
class Person {
    String name; // this is default access, making it private makes it inaccessible to it's
                 // descendants

    void printName() {
        System.out.println(name);
    }
}

class Student extends Person {
    int gpa;

    void show() {
        System.out.println(name + " has " + gpa + " GPA");
    }
}

public class Inherit {
    public static void main(String[] args) {
        Student s = new Student();
        s.name = "Sanchit"; // can assign super class' members too as long as they are default or public
        s.gpa = 10;
        s.show();
    }
}

```
- subclass has access to all public/default members of the super class. private members are not accessible.
- one subclass can have only one superclass.
- a reference variable of the superclass can be assigned a reference to any of the subclasses derived from that super class, but then only members of the super class will be accessible.
```java
// this is valid code
Person p = new Student();
        p.name = "Sanchit";
        p.printName(); // even if the type definition is of the superclass but the object is of type
                       // subclass, the overridden functions of the subclass will only be called, and
                       // overriding works normally.
        // p.show(); // this call on the other hand does not work because only members
        // of the
        // reference type can be accessed, so members defined in the subclass become
        // inaccessible.
```

## super 

- subclass refers to it's immediate superclass using the super keyword.
- can be used for two things:
    1. call the superclass' constructor
    2. access members of the superclass hidden by members of the subclass
- `super()` must always be the first statement executed in the subclass' constructor.
- super can be used to call any of the constructors or member methods defined by the superclass.
```java
class Rect {
    private int width;
    private int height;

    Rect(int width, int height) {
        this.width = width;
        this.height = height;
    }

    int area() {
        return width * height;
    }
}

class Sqr extends Rect {
    Sqr(int size) {
        super(size, size); // initialise the super class
    }

    int area() {
        return super.area();
    }
}

public class SuperStuff {
    public static void main(String[] args) {
        Sqr square = new Sqr(10);
        System.out.println(square.area()); // prints 100
    }
}

```
- see more in [SuperStuff.java](/class-code/SuperStuff.java).
- if we're doing multi level inheritance, a subclass inherits all members of it's super class, that super class inherits members of it's super class and so on.
- In a class hierarchy, if a superclass constructor requires arguments, then all subclasses must pass those arguments “up the line.” This is true whether or not a subclass needs arguments of its own.
- constructors complete their execution in the order of derivation, from the topmost superclass to the lowest childclass. this is because a super class has not knowledge of the initialisation needs of any of it's subclasses and must initialise first to let it's subclasses initialise properly.
- if super is not used the default constructor will be used.

## Method overriding 

- when a method in the subclass has the and type signature as the method in the superclass, the method in the subclass is said to override the method in the superclass.
- when an overridden method is called through the subclass, always the overridden method will be called. superclass' version is, in effect, hidden.
```java
class SuperClass {
    void superDuperFn() {
        //
    }
}
class SubClass {
    void superDuperFn() {
        // this method overrides the method as defined in the SuperClass and whenever superDuperFn will be called from an object of SubClass, always this variant of the method will be invoked.
        
        // if i still want to access the SuperClass variant of superDuperFn, i can do it by: 
        // super.superDuperFn();
    }
}
```
- Method overriding occurs only when the names and the type signatures of the two methods are identical. if not, they are simply overloaded.

## Dynamic Method Dispatch

- mechanism by which a call to a overridden method is resolved at runtime, rather than compile time.
- this is java's implementation of run time polymorphism.
- when a method is called through a superclass reference, Java actually determines (at runtime) which version of the method to execute depending on the type of object being referred to at the time the call occurs. the type of the actual object is the determining factor, not the reference type.
- run time polymorphism is important because it allows a general class to specify methods that will be common to all of it's derivatives, while allowing subclasses to determine specific implementation of some or all of those methods.
- it also allows the superclass to define methods that the derived class must implement it's own.

## Abstract Classes

- superclass defining structure of an abstraction but not providing an implementation of each method. although some methods can have a concrete implementation in the abstract class as well.
- the signatures of methods the subclasses must implement is specified in the superclass.
- methods can be required to be overriden by using the `absract` type modifier. such methods are also called subclasser responsibility.
- a class containing abstract methods must also be declared abstract.
- constructors, or static methods cannot be defined for abstract classes, and by extenstion, objects cannot be created for abstract classes.
- Any subclass of an abstract class must either implement all of the abstract methods in the superclass, or be declared abstract itself.
```java
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

        // even though SuperClass cannot be initialised, it can be used to create object references
        SuperClass ss = new SubClass(); 
        ss.abstractFn();
    }
}
```

## final keyword in the context of inheritance

- final can be used to prevent a method from being overridden
- Methods declared as final can sometimes provide a performance enhancement: The compiler is free to inline calls to them because it “knows” they will not be overridden by a subclass. When a small final method is called, often the Java compiler can copy the bytecode for the subroutine directly inline with the compiled code of the calling method, thus eliminating the costly overhead associated with a method call. Inlining is an option only with final methods. Normally, Java resolves calls to methods dynamically, at run time. This is called late binding. However, since final methods cannot be overridden, a call to one can be resolved at compile time. This is called early binding.
- we can mark a class as final, and as a result, no subclasses can be extended or inherited from a final class.