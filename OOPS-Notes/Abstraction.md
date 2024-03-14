# Abstraction

- A user does not need to worry about the implementation details of an object. We only care about the interface to that object and we can use the interface directly without worrying about how the object is implemented and build on top of the interface.
- Abstraction: hierarchal org of objects where each obj defines it's own behaviour. Sequence of instructions (messages) being passed between objects.

## Encapsulation

- A way to achieve abstraction. Data and code is kept safe from outside interference by way of protective wrappers and well defined interfaces that allow access to internal data and code.
- Similar functionality can be implemented in different ways but a uniform interface to access the functionality lets us not worry about the implementation details.
- Encapsulation in Java is implemented using Class. An object is an instance of a class. A class consists of the member methods and data.
- Members can be public or private. Public members can be accessed by any code inside or outside the class, while private members can be accessed only be members of the class. it is important to be careful of classifying what is private and what is not.

### Access Modifiers

| Access Modifier | within class | within package | outside package by subclass only | outside package |
| --------------- | ------------ | -------------- | -------------------------------- | --------------- |
| private         | Yes          | No             | No                               | No              |
| default         | Yes          | Yes            | No                               | No              |
| protected       | Yes          | Yes            | Yes                              | No              |
| public          | Yes          | Yes            | Yes                              | Yes             |

## Inheritance

- Inheritance allows a class to inherit the properties of a parent class and modify/extend it. This allows hierarchal organisation of data, and reduces requirement to define common characteristics between parent & child class.

## Polymorphism

- the same interface behaves differently in different situations; one interface => multiple methods.

- Encapsulation, interheitance, and polymorphism are the three pillars of OOP, they enable writing maintainable, readable and flexible code.
