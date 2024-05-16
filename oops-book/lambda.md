# Lambdas

- essentially an anonymous method defined by implementing a method in functional interface which itself is an interface that contains one and only one abstract method
- functional interface represents a single action and defines the target type of a lambda expr
- functional interface aka SAM (Single Abstract Method)
- A functional interface may specify any public method defined by Object, such as equals( ), without affecting its “functional interface” status. The public Object methods are considered implicit members of a functional interface because they are automatically implemented by an instance of a functional interface.
- lambda operator (`->`) divides the lambda expr into two parts. The left side specifies the params required (none => empty param list), and the right side specifies the lambda body which can be a single expr or a block of code

```java
// example lambda

() -> 123.45 // a lambda that returns a const value

() -> Math.random() * 100

(n) -> (n % 2) == 0
```

- a lambda expression can be specified only in a context in which a target type is defined. One of these contexts is created when a lambda expression is assigned to a functional interface reference. Other target type contexts include variable initialization, return statements, and method arguments, to name a few.
- a functional interface can be used to execute any lambda expr as long as it is compatible with it
- in these kinds of lambdas the return type is automatically inferred

```java
// !!! The below are all are aka expression bodies aka expression lambdas

// define a functional interface
interface NumInterface {
    // the following function defines that the method expects no parameters and returns a double value => the defined lambda should also follow this signature to keep the types compatible
    double getValue();
}


// this is another functional interface and the signature of the lambda defined by this interface is (int) -> boolean
interface InterfaceWithArg {
    boolean func(int x);
}

// this is another functional interface and the signature of the lambda defined by this interface is (int, int) -> boolean
interface MoreThanOneArg {
    boolean func(int x, int y);
}

psvm() {
    NumInterface num; // reference to a NumInterface instance
    num = () -> 123.45; // lambda in assignment context

    // here an instance of a class is automatically created that implements the functional interface where the lambda expr defines the abstract method in the functional interface

    // the lambda can then be exec as
    System.out.println(num.getValue());

    InterfaceWithArg i2;
    // this lambda receives one parameters and returns a boolean type value
    // here the type of n is inferred from the context, which in this case is the type of the parameter in the `func` method of InterfaceWithArg
    i2 = (n) -> n % 2 == 0;
    // this lambda can also be described with n's type explicitly mentioned
    i2 = (int n) -> n % 2 == 0;
    // since the lambda has only parameter, we can skip the paranthesis around n
    i2 = n -> n % 2 == 0;

    if (i2.func(2))
        System.out.println("2 is even");

    MoreThanOneArg i3;
    // lambda with 2 parameters. now always need to surround params with parenthesis
    i3 = (x, y) -> x % y == 0; // see if y is a factor of x;
    // if we wish to declare the types of certain params, we must define the params of all the params
    i3 = (int x, int y) -> x % y == 0; // legal
    i3 = (int x, y) -> x % y == 0; // !!! ILLEGAL

    if (i3.func(4, 2))
        // do something
}
```

## Block Lambdas

- here right side of `->` is a block of code with > 1 statement aka the block body
- lambda of these kind aka block lambdas

```java
interface FactorialInterface {
    int fn(int x);
}

psvm() {
    // decleration and init in one line
    FactorialInterface fact = (int x) -> {
        // the body can be thought of like a method body and everything legal inside a method body is legal in this block
        int result = 1;

        for (int k = 1; k <= x; k++)
            result = k * result;

        // since there is > 1 statements in the lambda, the return value needs to be explicitly specified
        return result; // return only from the lambda, not the calling fn
    };

    System.out.println(fact.fn(5));
}
```

## Generic Functional Interfaces

- lambda cannot itself be generic but the interface defining the lambda can be

```java
// a parameterised interface
interface GenInterface<T> {
    T func(T t); // lambda should take a param of type T and return a value of type T
}

psvm() {
    // here the type of str is automatically inferred to be String since that is the parameterised type of the generic interface
    GenInterface<String> revStr = (str) -> {
        // lambda body
    };

    // now here the type is Integer and n is automatically typed as an Integer
    GenInterface<Integer> fact = (n) -> {
        // lambda body
    };
}
```

## Lambda expr as arguments

- pass executable code as an argument
- to do so, the type of the receiving parameter must be of a function interface type compatible with the lambda

```java
interface StringFn {
    // define an abstract method: (str) -> str
    String func(String x);
}

class Lambdas {
    static void op(StringFn fn, String ip) {
        // the first param can receive any instance of StringFn

        String x = fn.func(ip);
        System.out.println(x);
    }

    public static void main(String[] args) {
        op((str) -> {
            // some functionality
            return str;
        }, "Hello World!");

        StringFn fn = (str) -> {
            // some functionality
            return str;
        };

        op(fn, "Yo this is another lambda");
    }
}
```

## Exceptions in Lambdas

- lambdas can only throw those checked exceptions (those checked at compile time as opposed to runtime which are called unchecked exceptions) which are in the throws clause of the abstract method

```java
interface Abs {
    // without throws clause here, the lambda would not be able to throw this checked exception
    double func(double[] x) throws EmptyArrayException;
}

class EmptyArrayException extends Exception {
    EmptyArrayException() {
        super("Supplied array is empty");
    }
}

class Lambdas {

    // the exception thrown by the lambda is handled by the main method, without this the code won't compile because it would be an unhandled exception
    public static void main(String[] args) throws EmptyArrayException {
        double[] vals = {1.0, 2.0};

        Abs abs = (x) -> {
            double sum = 0;

            if (x.length == 0) throw new EmptyArrayException();

            for (int k = 0; k < x.length; k++) sum += x[k];

            return sum/x.length;
        };

        System.out.println(abs.func(vals));
        System.out.println(abs.func(new double[0]));
    }
}
```

## Variable Capture

- variables in the scope enclosing the lambda are accessible inside the lambda, but these variables should be final or _effectively final_
- it also has access to `this` which ref to the invoking instance of the lambda expr's enclosing class
- lambda does not have a `this` of their own

## Method references

- method reference -> refer to a method without executing it
- it requires a target type context that consists of a compatible functional interface

### Static method references

- create static method ref using `ClassName::methodName`

```java
// this is the interface which is the paramter of a fn which expects a lambda
interface Int {
    String fn(String x);
}

class One {
    // this class defines the static method that will be referenced
    // this static method can only be referenced by lambdas that match the method signature aka be compatible with the Functional interface being used
    static String someFn(String x) {
        // do some ops
        return x;
    }
}

class Main {
    // a static method that expects a lambda compatible with the funcitonal interface defined by Int
    static String fn(Int i, String st) {
        return i.fn(st);
    }

    public static void main(String[] args) {
        String a = "Hello World!";

        String b = fn(One::someFn, a); // here One::someFn is a reference to the method without invoking it
        System.out.println(b);
    }
}
```

### Instance method references

- similar syntax except this time we use `objName::methodName` where objName is a concrete object of a class

```java
// this is the interface which is the paramter of a fn which expects a lambda
interface Int {
    String fn(String x);
}

class One {
    // this class defines the method that will be referenced
    // this method can only be referenced by lambdas that match the method signature aka be compatible with the Functional interface being used
    String someFn(String x) {
        // do some ops
        return x;
    }
}

class Main {
    // a static method that expects a lambda compatible with the funcitonal interface defined by Int
    static String fn(Int i, String st) {
        return i.fn(st);
    }

    public static void main(String[] args) {
        String a = "Hello World!";

        One o1 = new One();

        String b = fn(o1::someFn, a); // here o1::someFn is a reference to the method without invoking it via a concrete object of the One class
        System.out.println(b);
    }
}
```
