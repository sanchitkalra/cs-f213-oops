# Classes

- basis for object oriented programming 
- all concepts encapsulated as classes
- compiled code is stored as a `.class` file
- class itself is a datatype, and once defined we can create objects of this datatype
- obj => instance of a class
- declared using `class` keyword
- by using a class we don't need to care about inner workings and they can be changed as and when needed without causing side effects outside the class
- typical clas:

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

- 