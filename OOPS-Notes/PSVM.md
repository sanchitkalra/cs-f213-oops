# PSVM

_public static void main_

- public: this special function needs to be public so that it can be accessed and invoked by the JVM.
- static: static methods inside classes need not the class be initalised be instantiated. In this context, we are telling the JVM that the function main needs to be run without instantiating the class inside which main occurs. It directly calls `ClassName.main()`. The benefit is using this technique we can save a lof of memory.
- void: in java programs, main does not return anything (because it has to be called by the JVM) and hence the return type of the main function is void.
- main: this is just the name of the function the JVM looks for as the entry point to the program. It can take an arrays of strings representing the command line arguments.
