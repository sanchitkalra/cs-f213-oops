# Packages & Interfaces

_this document refers to ch9 of the textbook_

- packages -> containers for classes. they keep the classname space compartmentalised.
- stored heirarically, imported into classes.
- interface -> fully abstract interface from implementation. specify methods that can be implemented by one or more classes.
- an interface does not have an implementation. over simple abstract classes, a class can implement more than one interface

# Packages

- package -> naming + visibility control mechanism.
- define classes inside a package that are not accessible by code outside the package.
- can define class members visible only to code of the same package.
- create a package by simply writitng `package <packagename>;` as the first statement.
- can create a hierarchy of packages by seperating package names by a `.`.
- packages can be found in subdirectories, or a directory path can be specified by setting the `CLASSPATH` variable, or can be given with the `--classpath` command line argument.
- when executing class file using `java` on the terminal, full qualified name must be used which includes the package name, eg: `java pkgname.ClassName`
- whenever something does not have an access specifier (default access specifier), it is accessible by subclasses and other classes in the same package.
- packages can be imported using import statements. once imported, a class can be referred to directly by only using it's name.
- import statement occurs directly after the package declaration.
- `java.lang` is implicitly imported by the compiler for all programs.
- if a class with the same name exists in two different packages, you have to explicitly specify from which package do you want to use one of the classes or a compile time error will happen.

# Interfaces

- using interface keyword, we can fully abstract a class' interface from it's implementation.
- using interface, we define _what a class must do, not how it does it_.
- interfaces are similar to classes but do not have instance variables and hence no state.
- any number of classes can implement an interface, and a class can implement multiple interfaces.
- implement an interface => a class provides the full set of methods required by the interface.
- interfaces disconnect the definition of a method or set of methods from the inheritance hierarchy. Since interfaces are in a different hierarchy from classes, it is possible for classes that are unrelated in terms of the class hierarchy to implement the same interface. This is where the real power of interfaces is realized.
- interfaces can also have access specifiers, and if an interface is public, it should be the only public declared interface in a file.
- variables declared inside an interface are implicitly final & static => cannot be changed by the implementing class. all methods and variables are also public by default.
- example: 
```java
interface Callback {
    void callback(int params);
}
```

## Implementing Interfaces

- a class can implement >= 1 interface(s) using the `implements` keyword.
- if two interfaces declare the same method, and a class implements these two interfaces, the same method implementation will be shared by the two clients of either interfaces.
- The methods that implement an interface must be declared public. Also, the type signature of the implementing method must match exactly the type signature specified in the interface definition.
- there is no restriction on classes implementing additional functions apart from the methods being specified by the interface.
- variables can be declared as object references using interfaces and such variables can hold objects of classes that implement the interface. whenever a method is called, the correct method will be called depending on the actual instance of the interface being referred to. such variables will only have the information for the members of the interface and not the actual class that implements them and as such cannot access other members of the implementing class.
- if a class includes an interface but does not implement all the methods required by that interface, it must be marked abstract.

## Nested Interfaces

- an interface can be declared as member of a clas or another interface.
- nested interfaces can be public, protected, or private unlike top level interfaces (only public or default).
- when a nested interface is used, it must have the full qualified name including the name of the class/interface of which it is a part.
```java
class A {
    interface B {
        int x();
    }

    class C implements A.B {
        int x() {
            System.out.println("fn!");
            return 0;
        }
    }
}
public class Main() {
    public static void main(String[] args) {
        A.B obj = new C();
        obj.x();
    }
}
```

## Variables in interfaces

- variables in interfaces, being final and static, can act as shared constants across multiple classes that implement the said interface.
- if an interface does not have any methods, and only variables, it is like importing constants as final in a class.
- _this is controversial and not recommended_

## Extending Interfaces

- one interface can inherit another by using extends.
- when a class implements an interface that extends another interface, the class must provide definition for all methods required by the inheritance chain.

## Default Interface Methods

- default methods -> specify a default implementation for an interface method.
- aka extension method
- useful when adding new methods to code but ensuring old code doesn't break.
- default implementations need to be prefixed by the keyword default
```java
interface ShowcasingDefaults {
    int getNum(); // this is a normal interface method declaration.

    // this is a method which provides a default implementation and is prefixed by the default keyword.
    default int getFancyNum() {
        return 24;
    }
}
```
- for default methods, it is not necessary that implementing class override it.
```java
class Impl implements ShowcasingDefaults {
    // this class only implements getNum and that is perfectly fine
    int getNum() {
        return 10;
    }
}

class Main {
    public static void main(String[] args) {
        Impl i = new Impl();

        // following both calls succed
        System.out.println(i.getNum());
        System.out.println(i.getFancyNum()); // uses default implementation
    }
}
```

## Static Methods in interfaces

- static method in an interface can be called independetly of an object. no implementation or instance of the interface is necessary.
```java
public interface Interface {
    static int getDefaultNum() {
        return 0;
    }
}
public class Main {
    public static void main(String[] args) {
        int defNum = Interface.getDefaultNumber();
    }
}
```
- static interface methods are not inherited by either an implementing class or a subinterface.

## Private methods in interfaces

- a private method in an interface can only be called by a default method or another private method.
- it cannot be used by code outside the interface or by subinterfaces.
- The key benefit of a private interface method is that it lets two or more default methods use a common piece of code, thus avoiding code duplication
- if a private method uses functions left to a class to implement, it will use the implementation provided by the class that implements this interface.