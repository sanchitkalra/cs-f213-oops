# Generics

_this document corresponds to ch 14 of the textbook_

- create classes, interfaces, and methods that work in a type safe manner with various kinds of data
- define algo once independent of data type
- generics -> _parameterised types_
- type specified as parameter
  - convention states that these type names be a single upper case alphabet
- class, interface, or method that operates on a paramterised type _is called a generic_
- before generics, the `Object` type was used to write code that could work with multiple data types -> generalised classes, methods etc. but disadvantage of this approach is that we loose type safety
- defined as:

```java
// T -> will be replaced with an actual type when GenericClass will be instantiated
class GenericClass<T> { // T -> type parameter
    T objectOfTypeT;
    ... // other member variables

    GenericClass(T o) {
        this.objectOfTypeT = 0;
    }

    // similarly other methods that use T can be specified
    ...
}

// this class can be used as:
GenericClass<String> genericOfTypeString;
genericOfTypeString = new GenericClass<String>("stringggg"); // type argument is also specified for the constructor being used
```

- here T is the type that will be supplied on creation of an object of this class
- `var` cannot be used as the name of a type parameter
- compiler does not create different versions of the generic class but substitutes the necessary casts -> called erasure
- generics only work with reference types (a class type) and not primitive types
- a generic variable of one kind cannot be used with a generic of another kind
- if generics are not used, explicit casts will be required everywhere, because the generic Object type was used
  - this can cause a lot of bugs -> not recommended
- generics can also have more than one type specified

```java
class TwoGenerics<T, V> { // both T and V can be same or different types
    ...
}
```

- the accepted types for a generic class can be bounded to certain classes by defining a superclass from which all type args must be derived
  - T can only be replaced by superclass, or subclasses of superclass, _a bounded uppper limit is set_
  - types that do not extend from the superclass cannot be used to instantiate the generic class
  - in addition to classes, multiple interfaces can also be specified as bounds
- lower bounds can also be specified by using the `super` keyword as `<T super subclass>`

```java
class BoundedGeneric<T extends SuperClass> {
    ...
}

class IntersectionTypeBound<T extends SuperClass & SomeInterface> {
    ... // now this class is bounded by SuperClass and SomeInterface and the type is now called an intersection type
    // T must now be a subclass of SuperClass and implement SomeInterface
}
```

- wildcard arguments can be used to opt-out of type safety and use generics without a type parameter
  - this argument is specified by the `?` type
  - we don't need to specify the bounds rules here because they act before the object is created and no object can be created and subsequently passed to the wildcard generic if it doesn't exist because of the bounds in the first place
  - but if we wish, we can still bound them by using the same syntax as for generic types

```java
// say there is a class that calculated the averages of an array passed to the constructor
// we would like to know if averages for two different types of arrays is the same
boolean isSameAvg(Stats<?> ob) { // here ? is the generic type, and ob can match any Stats object
    if(average() == ob.average())
        return true;
    return false;
}

void someFunc(SomeObject<? extends SomeSuperClass> ob)  {
    // use ob here with the rule that whatever object is passed here is either of type SomeSuperClass or it's descendant
}
```

- if the generic type is not declared during class instantiation, it is set to object by default and this is for backwards compatibility reasons for code that was written before Generics were introduced. this is called a raw type and type safety is lost

```java
Gen raw = new Gen(Double.valueOf(98.6)); // here Gen's parameter type is Object
```

- a variable of a raw type can be assigned a reference to any type of Gen object. The reverse is also allowed; a variable of a specific Gen type can be assigned a reference to a raw Gen object. However, both operations are potentially unsafe because the type checking mechanism of generics is circumvented. for example consider the following

```java
// raw type
Gen raw = new Gen(Double.valueOf(98.6));

// integer type
Gen<Integer> genInt; // assume instantiation

// string type

Gen<String> genString; // assume instantiation

// the following code breaks and run time error
int i = (Integer) raw.getOb(); // because raw is of type object and the value contained is a double which cannot be convered into an integer

genString = raw; // this would not cause errors at compile time but could cause issues at runtime, so just don't do it

// this would now blow up at runtime
String str = strOb.getOb();

// the following code will also blow up
raw = genInt;
d = (Double) raw.getOb(); // blows at runtime, cast assumes object inside raw is an object and can be casted to Double but that is not valid
```

- generic classes can also be used as subclasses and superclasses

```java
class SuperGenClass<T> {
    ...
}

class SimpleSubGenClass<T> extends SuperGenClass<T> {
    // here the subclass extends a generic superclass and the subclass also needs to be typed and reference the type for the superclass
}

class NonGenClass extends SuperGenClass<T> {
    // this wouldn't work becuase now the compiler does not know where to reference T from
    // even if the sole use of the type parameter is to satisfy the type parameter requirement of the superclass, it must be specified, and the subclass must be generic
}

class GenSubClassWithMultipleTypeParams<T, V> extends SuperGenClass<T> {
    // this is completely valid, the requirement of the superclass must be satisfied while the subclass is free to add any requirements of it's own
}

class GenSubclass<T> extends NonGenericSuperClass {
    // a subclass can be typed while the superclass is not and it's completely valid
}
```

- we can check if an object is some kind of a generic type by using the `instanceof` operator

```java
class Gen<T> {}
class Gen2<T> extends Gen<T> {}

Gen<Integer> iOb = new Gen<Integer>(88);

if (iOb instanceof Gen<?>) // true
if (iOb instanceof Gen2<?>) // false

Gen2<Integer> iOb2 = new Gen2<Integer>(99);
Gen2<String> strOb2 = new Gen2<String>("Generics Test");

if (iOb2 instanceof Gen<?>) // true because Gen is a superclass of Gen2; also true for strOb2
if (iOb2 instanceof Gen2<?>) // true; also true for strOb2
```

- generics can also be casted from one type to another

```java
// carrying the Gen and Gen2 types and their instances

(Gen<Integer>) iOb2 // legal cast as Gen is a super class of Gen2 of which iOb2 is an object

(Gen<Long>) iOb2 // illegal because iOb2's type is not Long
```

- methods can be overridden in subclasses
- specifying types in the constructor is legal since JDK 7
  - uses `<>` which is called the diamond operator and is basically an empty argument list -> tells compiler to infer types needed by the new argument
  - this can also be applied to when a Generic type is used as a parameter to a method

```java
// letting the compiler figure out the types using the diamond operator
Gen2<String> gen = new Gen2<>("using diamond operator");

// method example
void someFn(Gen2<T, V> obj) {
    ...
}
// and the method being called as
someFn(new Gen2<>(66));
```

- we can also use the var keyword and let the compiler figure out the type conversely as well

```java
var myObj = new Gen2<Integer>(66); // type of myObj is inferred from the initialiser
```

## Generic Methods

- it is also possible to create generic methods. they don't necessarily have to be inside a generic class, and even if they are they can take more parameters for generic types than the generic class takes. the type parameters are specified before the return type of the string

```java
// the following is a method that checks if an element is present in an array
// here T must be a subclass of Comparable to use the `.equals` fn
// V must also be a subtype of T, so that can be compared to T
static <T extends Comparable<T>, V extends T> boolean isIn(T x, V[] y) {
    for(int i=0; i < y.length; i++)
        if(x.equals(y[i]))
            return true;

    return false;
}

// it can then be called simply like this
isIn(2, nums);

// if you want to specify the types manually, it can be done like this
GenMethDemo.<Integer, Integer>isIn(2, nums); // GenMethDemo -> class where this static method was defined
```

## Generic Constructors

- constructors can be generic even if their parent class isn't

```java
class GenCons {
    private double val;

    // here `<T extends Number>` specifies the generic type to be used for the method's parameters
    <T extends Number> GenCons(T arg) {
        val = arg.doubleValue();
    }

    void showVal() {
        System.out.println("val: " + val);
    }
}
```

## Generic Interfaces

```java
interface MinMax<T extends Comparable<T>> {
    T min();
    T max();
}

// a class would now implement this generic class as
// important to note that the class implementing the interface must also specify the same bounds on the type T as the interface
class MyClass<T extends Comparable<T>> implements MinMax<T> {
    ...
}

// and the class would be used as
MyClass<Integer> iob = new MyClass<Integer>(inums);

// if we would only like to implement the interface for a specific type, we can do it as
class MyClass implements MinMax<Integer> {
    ...
}
```

## Limitations on Generics

- they cannot be instantiated `new T(); // this is illegal`
- static members cannot use type parameters
  - static methods can have parameters which define their own types but it's return type cannot be a type parameter itself
- cannot instantiate arrays of parameter types
- generic classes cannot extend Throwable

```java
class StaticGen<T> {
    static T ob; // illegal
    static T fn() {} // illegal

    T[] vals; // ok
    vals = new T[10]; // illegal; instantiation is the issue, using types for arrays is not

    StaticGen(T[] array) {
        this.vals = array; // this is legal because we're assigning a reference to an existing array, not creating a new one
    }

    psvm() {
        // consider this to be the program's main function

        Gen<Integer>[] gens = new Gen<Integer>[10]; // this is illegal
        Gen<?>[] gens = new Gen<?>[10]; // this is legal
    }
}
```
