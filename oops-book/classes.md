# Classes

_this document refers to ch6 and ch7 of the textbook_

- basis for object oriented programming 
- all concepts encapsulated as classes
- compiled code is stored as a `.class` file
- class itself is a datatype, and once defined we can create objects of this datatype
- obj => instance of a class
- declared using `class` keyword
- by using a class we don't need to care about inner workings and they can be changed as and when needed without causing side effects outside the class
- typical class:

```java
class Name {
    // data of the class is called instance variables
    type instance-var1; 
    type instance-var2;
    ...

    // code is contained in methods
    type method1(args) {}
    type method2(args) {}
    ...
}
```
- each obj receives it own copy of instance variables when it is initialised
- class definition is not the same as creating an actual object of a class. initialisation:
```java
Name varName = new Name(); // "Name" is the name of the class we're instantiating
```
- in the above ex, varName now refers to an instance of Name
- to access public memebrs of an object we use dot notation (`varName.member`). here member can be any variable or function
- a variable of a particular data type is a container that *can* hold an object of that data type. we need to explicitly initialise an object and assign it to a variable which will hold that object
- objects can be created using the `new` operator. it dynamically allocates memory for that object and returns a reference to it. the variable then stores this reference
    - if sufficient memory is not available and new cannot allocate memory => runtime execption will occur
```java
Name obj; // create a variable
obj = new Name(); // create an object using the new keyword and then assign the reference to the variable
```
- the statement after new (here: `Name()`) is called the constructor of the class. it defines what occurs when an object of a class is created
- no constructor specified => java used the default constructor
- primitive data types like int, float, char are not implemented as objects but rather as normal variables. results in better efficiency
- if we create a new variable and point it to an already existing variable, the new variable also points to the same object the existing variable pointed to. this allocates no new memory or make copies of the original object. any changes made through the new variable also affect the object to which b1 is referring since they are the same object
```java
Name n1 = new Name();
Name n2 = n1; // here n2 also points to the object to which n1 points
```
- n1 can be unhooked from the object without having an effect on n2
```java
n1 = null; // n2 still points to the object originally referred to by n1
```

## Methods

- a method has a return type (in case of nothing being returned, `void`), a name, paramter list, and a body
    - parameters are essentially variables that receive values of the arguments passed to the method when it is called
- we usually use methods to access instance variables. this is useful to hide specific layout of internal data structures and build cleaner abstractions
- when a method is called, the control transfers to the code inside the method and once the code exectution inside the method is complete, control is tranferred back to the calling routine
- a method can access the class' variables without the dot notation because a method is always invoked with reference to some class' object and hence there is no need to specify the object again
- values can be returned from methods using the `return` keyword
```java
retType method1() {
    ... // some code
    return value; // this value must be of the type retType
}
```
```java
// the val can be retieved as
retType ret = object.method1(); // the variable type here should be the same as the method's return type
```

## Constructors

- a constructor initialises an object immediately after creation => creates usable object 
- same name as the class and *syntactically* similar to a method
```java
class Box {
    Box() { // constructor with no parameters
        // some code
    }

    Box(int w1, int w2) { // constructor with parameters
        // some code that utilises the parameters
    }
}
```
- called when obj is created and before new operator completes
```java
class-var = new ClassName(); // here the constructor with no parameters for ClassName is called
class-var2 = new ClassName(arg1, arg2); // constructor with 2 arguments called
```
- implicit return type of constructor -> class type
- if no constructor is defined, Java's default constructor is used. all uninitialised vars have their default values

### this keyword

- used to refer to the *current* object => this is a reference to the object on which the method was invoked
- can be used anywhere a reference to an object of the current class' type is required
- useful to resolve namespace collissions. if the name of an instance variable and a parameter are the same, the parameter variable hides the instance variable. to specifically point to the instance variable the this keyword can be used
```java
Box(int width, int height) { // these parameters hid the instance variables
    this.width = width; // this.width refers to the instance variable, while just width refers to the parameter
    this.height = height;
}
```

## Garbage Collection

- java handles deallocation of objects automatically using a technique called garbage collection
- when no references to an object exist, java assumes that object is no longer needed and the memory occupied by that object can be reclaimed

## Method Overloading

- two or more methods defined with the same name with different parameter declarations
```java
// here the function test is overloaded, one without any parameters, and one with just one parameter
void test() {
    ...
}
void test(int a) { 
    ...
}
```
- a way of polymorphism
- java automatically determines which version of the overloaded method to call, essentially the method who parameters match the arguments passed
- the return type alone is **insufficient** to distinguish between two versions of a method => return value is of no consequence relative to overloading
- in some cases, java's automatic type conversions can also play a role in overload resolution which happens only if an exact match is not found
```java
// say we have the following two overloaded methods
void test(double a) {} // version 1
void test(int a, int b) {} // version 2  

// now we call them
// say ob is the object of the class where these functions test are defined

int a = 10; 
int b = 20;
double c = 25.4;
int i = 34;

test(a, b); // this calls version 1
test(c); // this calls version 2
test(i); // this calls version 1!

// this happened because java can automatically convert an int into a double and this conversion can be used to resolve the call. java evelates int to double and calls the function
```
- this impelements polymorphism as it is one way Java implements "one interface, multiple methods"
- this allows the programmer to only care about the general operation to be performed and the compiler chooses the correct version for a particular circumstance

## Constructor Overloading

- like overloading methods, we can overload constructors too
```java
class Box {
    Box() { // constructor with no params
        ...
    }
    Box(int height, int width) { // constructor specifying two properties
        ...
    }
    Box(int side) { // constructor specifying only one property
        ...
    }
}
```

## Using objects as parameters

- we can use not only built in types, but objects as parameters too
```java
class Test {
    boolean someFunction(Test o) { // an object of type "Test" passed to this function
        ... // some logic
    }
}
```
- any classes defined by the user can be used in the same way as any of the builtin Java types
- to create copies of an object, we can also pass another object to the constructor
```java
class Box {
    int width;
    ... // other instance variables
    Box(Box otherBox) {
        width = otherBox.width;
        ... // assign other instance variables from the old object to the new object
    }
    ... // other usual constructors & methods
}

// we can use it as follows
Box b1 = new Box();
Box b2 = new Box(b1); // a copy of b1 is created this way
```

## Argument Passing

- two ways to pass arguments to a function
    - call by value: the value of the argument is copied into the formal parameter of the subroutine. changes made to the parameter of the subroutine have no effect on the argument
    - call by reference: reference to the argument is passed to the parameter. changes made to parameter is will affect the argument used to call the subroutine
- primitive types are passed as values
- objects are effectively passed by reference. when an object of a class is passed, what is receieved is a reference to the object and any changes made to the object inside the method *do affect* the object passed into the argument. *the variable for  an object is a reference and the reference itself is passed by value, but the actual object is, in effect, passed by reference*

## Returning Objects

- methods can return any types of values, including class types
```java
class Test {
    int a;

    Test(int i) {
        a = i;
    }

    Test increment(int x) {
        Test t = new Test(a + x);
        return t; // here we're returning a value of type Test
    }
}
// we can use it as:
Test ob1 = new Test(1);
Test ob2 = ob1.increment();
```
- each time a function returning a class type is invoked, a new object is created, and a reference to that object is returned
- the object inside the function is allocated using new, the object continues to exist, even though the method terminates as long as there is a reference to the object somewhere in the program. after no references are left, the garbage collector will reclaim the memory used by the object

## Recursion

- when a method calls itself
- when a method calls itself, new local variables and paramaters are allocated storage on the stack and the method code is executed with these new variables from the start, and as these recursive calls return, the old local variables and parameteres are removed from the stack.
- recusrive routines are slower than iterative routines, because of the additional overhead of additional method calls.
- too many recursive calls can lead to a stack overflow exception.
- all recusrive methods must containing a breaking condition, or the recursive method will never return and cause a stack overflow.

## Access Control

- how a member of a class is accessed is controled by access modifiers.
- **public**: the member can be accessed via other code outside the class as well.
- **private**: the member can only be accessed via code (other members) inside the class.
- main() is always public because it is called by code outside the program and hence needs to be public to be accessible to it.
- **default**: when no access specifier is used, the default is this access level. here the members are public within the package, but private outside this package.
- the access modifier is written before the type specification of the member. example: `private int count;`. here private is the access specifier, amd int is the type.

## Static keyword

- usually members of a class are accessed within the context of an object of that class, but if we want to access certain members without instantiating a class, we can use the keyword `static`. 
- static members can be accessed before any objects are create and without reference to any object.
- main() is static because it is called before any objects exist. 
- instance variables declared static are in effect global variables. hence when objects of a class are declared, no copies of the static variables are made, all instances of the class uses the same static variable.
- static method limitations:
    - they can only call other static members
    - they can only access other static variables
    - cannot refer to `this` or `super` in any way.
- example:
```java
// Demonstrate static variables, methods, and blocks.
class UseStatic {
    static int a = 3; // 1. a is initialised to 3.
    static int b;

    // 4. finally meth runs and prints all the values
    static void meth(int x) {
        System.out.println("x = " + x);
        System.out.println("a = " + a);
        System.out.println("b = " + b);
    }

    static { // 2. this static block runs and inits b to a*4. 
        System.out.println("Static block initialized.");
        b = a * 4;
    }
    
    // 3. then this main block runs and makes a call to meth.
    public static void main(String[] args) {
        meth(42);
    }
}
```
- outside of the class in which they are defined, static methods and variables can be used independently of any object.
- calling a static method: `ClassName.methodName();`. here ClassName is the actual name of the class & not an instance of the class.

## final keyword

- a field declared as `final` cannot be modified, in effect making it a constant.
- example: `final int AGE = 21;`. now age is set to 21 and can never be modified. it is commmon to use upper case names for constants.
- declaring a parameter final prevents it from being changed inside the method.

## Nested Classes

- a class defined within another class.
- scope bounded by the scope of the enclosing class. the class can be defined anywhere, even when they're not members of the enclosing class, for example when declared inside a for loop or an if condition block.
- an instance of a nested class can only be created in the context of the enclosing class.
- if B is defined inside A => 
    1. B cannot exist independently of A
    2. B has access to all private members of A, but vice versa is not true
    3. B is a member of A
- two types of nested classes:
    1. static nested class. has the static modifier applied and cannot access the non static members of the enclosing class without an object
    2. inner class. non static nested class. access to all variables and methods of the outer class in the same way any other member of the class has access
- inner class example:
```java
class Outer {
    int x = 10;

    void test() {
        Inner inner = new Inner();
        inner.display();
    }

    class Inner {
        void display() {
            System.out.println(x);
        }
    }
}

class Main {
    public static void main(String[] args) {
        Outer outer = new Outer();
        outer.test();
    }
}
```

## Strings

- all strings (even string constants) are really objects of type String.
- Strings are immutable.
- peer classes of String: StringBuffer & StringBuilder.
- can use concatenation (`+`)
- common methods from the String class are `boolean equals(secondString)`, `length()`, `char charAt(index)`.
- array of strings: `String[] arrayOfStrings = {"one", "two", "theee" };`

## Command Line Arguments

- command line arguments are passed as strings and are passed as the args parameter to the main method.
- for example, if the program invocation was as: `java CommandLineExample 1 2 3 abc`, the array would hold `1 2 3 abc` all as strings.

## Variable Length Arguments

- if we'd like a method to accept a variable sized amount of arguments each time that method was called, we can construct the function in the following way:
```java
void myMethod(int ... v) {
    // my method can be called with 0 to any number of arguments.
    // v is implicitly declared as an array of int, but theoretically you could you use any other data type in place of int.
    // if no arguments are passed to the function, the length of the array is zero.
    // we can iterate over v normally like any other array
    for (int x: v) {
        System.out.println(x);
    }

    // some logic for the method
}
```
- The ... syntax simply tells
the compiler that a variable number of arguments will be used, and that these arguments will
be stored in the array referred to by v. 
- a method may have AT MOST ONE variable length argument parameter.
- we can also combine normal parameters with variable length parameters, just that the variable length parameters should declared last the method. if the variable sized parameters is not in the end, it will be treated as incorrect and the compiler will throw an error.
```java
void order(int tableNumber, int ... items) {
    // say this method is for ordering items of a menu
    // now it needs an table number and the items that table order, which could be any number of items (potentially zero)
    
    // in this case the first argument is matched to tableNumber and any subsequent arguments are matched to items

    System.out.println("processing order for table number: " + tableNumber);

    System.out.println("Orders: ");
    for (int itemID: items) {
        System.out.println(itemID);
    }
}
```
- variable length parameter methods can be overloaded just like any other methods.
```java
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
```
- overload may also have certain pitfalls to be aware of
    1. say there are two version of a method one with the signature `void x(int n, int ... v)` and `void x(int ... v)`. now if you make a call to, say, x(1), such a call cannot be resolved because both functions can accept and handle this invocation, and this is an ambiguous call.
    2. say there are two version of a method one with the signature `void x(int ... v)` and `void x(boolean ... v)`. now say you call, x(). again the call is ambiguous and the compiler cannot decide which function to call for you.