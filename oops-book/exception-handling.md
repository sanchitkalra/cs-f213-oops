# Exception Handling

- exception -> abnormal condition (error) in a code sequence arising at runtime.
- object that describes the exceptional (or error) condition that has occured in a piece of code.
- when it happens, an object is created, and the object is thrown in the method that caused the error. 
- method may handle the exception itself, or pass it on. eventually the exception is caught and processed.
- exceptions can be generated by either the JVM (violate fundamental rules) or the code itself (report error conditions).
- five keywords: try (monitor code for exceptions), catch (catch an exception occuring in the try block), throw (manually throw an exception), throws (exception thrown out of a method), finally (code that must be executed after a try block is put in this block)
```java
try {
    
} catch (Excp1 ob) {

} catch (Excp2 ob) {

} finally {

}
```

## Exception Types

- all types subclasses of `Throwable`. Just below the Throwable level, all exceptions are divided into two branches: 
    1. one headed by `Exception` (exceptions user programs should catch, and our exceptions will be subclasses of this class). an important subclass is `RuntimeException`
    2. the other is Error, the exceptions not expecting to be caught. this is for errors related to the run time env itself like stack overflow. 