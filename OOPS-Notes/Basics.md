- The `main` function in a Java program is being called by the JVM (Java Virtual Machine) and hence needs to be public so that JVM has access to it, and since it is not expected to return anything, it's return type is void.
- Static: A static method can be called without instantiating a class. The main method needs to be static because the JVM needs to call it without instantiating main. Also not having to initialise an object also saves a lot of memory.
- Main takes one argument that is an array of strings that contains the command line arguments provided to the program by the person/process executing our program.
- `System.out.println("Hello World!");` Here we are calling the function `println` which is part of the `out` class in the `System` package.
- If the class is public, the filename should be the same as the class name but it is not a requirement if the class is private. By convention, all file names are the same as the class names.
- Conventionally there is at maximum public class in a single Java file.
- If we are declaring multiple `main` functions in a single java file, only one of the classes can be a public class whose name should be the same as the name of the file, and the main function that will be called will be of the class whose name is the same as file name.
  - Technically you can get away with not declaring any class public and in that case if your compile and then run, it takes the class whose name is the same as your file name to select the main function to invoke.
  - If in the other case that no class is declared public and you directly run the code with `java 'file-name'.java`, the first main method declared in the file will be used and no file name matching will happen because file name being same as the class name is a rule that only applies to public classes.
- There are 3 kinds of loops
  - for loop
  - while loop
  - do while loop : this loop is guaranteed to run at least once
- We **cannot** use access modifiers like private, public etc inside methods because all variables are scoped locally.
- We can create an instance of a class inside the class itself, although, this code will result in a stack overflow error because we're constantly instantiating objects inside objects infinitely.
```java
class NT {
  public NT n1 = NT(); // instance of a class inside a class
  // ... other methods defined as usual
}
```