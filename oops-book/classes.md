# Classes

- basis for object oriented programming 
- all concepts encapsulated as classes
- class itself is a datatype, and once defined we can create objects of this datatype
- obj => instance of a class
- declared using `class` keyword
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
- each obj receives it own copy of instance variables
- class definition is not the same as creating an actual object of a class. initialisation:
```java
Name varName = new Name(); // "Name" is the name of the class we're instantiating
```
