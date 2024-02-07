# Introduction
As program complexity increases, the need for Object Oriented Programming is felt. We programs evolve, we need to make the programs:
1. readable
2. maintainable
3. reusable

OOP languages add the following features/disciplines:
1. encapsulation
2. polymorphism
3. inheritance
4. abstraction
5. etc

In OOP programs, the attributes and behaviours are contained in a single object unlike procedural languages where attributes and behaviours are separate.

## Objects
Objects are the building blocks of OOP. An object oriented program is essentially a collection of objects. Think of attributes as variables of the class & behaviours as function of the class.

## Class
A class is a blueprint of the class, it is the building block of the object. An instantiated class is an object.

## Bytecode
- It was required that software written once run on a variety of platforms.
- As the software developer, now you don't need to worry for each different OS and hardware architecture. Java compilation produces byte-code that runs on Java Virtual Machines (JVM) and the byte-code executes independently of the underlying hardware as the JVM abstracts it away. The JVM executes programs in a sandboxed environment and that also provides additional security.
- Java code is first compiled to byte-code, and then JVM executes the byte-code. Early JVMs were interpreted but now modern JVMs use HotSpot technology (Just in Time (JIT) compilation), where certain parts of the code which would benefit from compilation are already compiled. Earlier Ahead of Time Compilation also used to exist but modern versions of Java don't support it.

## Two approaches to Programming
- procedural: programs built around code
- OOP: programs built around data and objects & interfaces to the data, the whole code becomes easier to manage.
