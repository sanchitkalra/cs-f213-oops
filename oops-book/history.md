# History

_this document refers to ch1 of the textbook_

- java solves two key problems: 
    1. security. programs running on a computer must not be able to do harm, and thus the JVM confines them to their execution enviornment.
    2. portability. programs written once should be executable on a number of different computers & operating systems.

- java solves these problems using **bytecode**. 
_Bytecode is a highly optimized set of instructions designed to be executed by what is called the Java Virtual Machine (JVM), which is part of the Java Runtime Environment (JRE)_
- original JVM => interpreter for bytecode
- once a JRE exists for a machines, any java program can run on it. only the JVM needs to be reimplemented for each machine.
- now since the JVM is in control of program execution, it can manage the program's execution and restrict it's execution enviorment to what is called a _sandbox_, preventing unauthorised access to the machine.