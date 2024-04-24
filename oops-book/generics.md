# Generics

_this document corresponds to ch 14 of the textbook_

- create classes, interfaces, and methods that work in a type safe manner with various kinds of data
- define algo independent of data type
- generics -> parameterised types
- type specified as parameter
- defined as:
```java
class GenericClass<T> { // T -> type parameter
    ...
}
```
- here T is the type that will be supplied on creation of an object of this class
- `var` cannot be used as the name of a type parameter
- 