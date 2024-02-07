# PSVM
*public static void main*

- public: this special function needs to be public so that it can be accessed and invoked by the JVM.
- static: static classes need not be instantiated. In this context, we are telling the JVM that the function main needs to be run without instantiating the class inside which main occurs. It directly calls `ClassName.main()`
- void: in java programs, main does not return anything and hence the return type of the main function is void.
- main: this is just the name of the function the JVM looks for as the entry point to the program
