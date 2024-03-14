Java is strongly typed. Each variable and expression must have a strictly defined type. Each assignment is type checked and automatic typecasting (coercing/conversion) doesn't happen.

## Primitive datatypes

They rep singular values. Not implemented as obj for efficiency reasons and have strict ranges so that compatibility is maintained across different systems (16 bit vs 64 bit systems and also backward compatibility).

### Integers

byte (8 bits), short (16 bits), int (32 bits), long (64 bits)

### Floating point numbers

float (32 bit) (floats declared by default are 64 bit; to change them to 32 bit we suffix `f` ), double (64 bit)
`float f = 3.14; // this is 64 bit`
`float f = 3.14f; // this is 32 bit`

### Characters

char (16 bit and compatible with 16 bit unicode)
```java
char ch1 = 88, ch2 = 'Y';
System.out.println(ch1 + "" + Y); // outputs XY
ch1++;
System.out.println(ch1 + "" + Y); // outputs YY
```
<!-- `char ch1 = 88; System.out.println("" + ch1); // outputs X`
`System.out.println("" + 88); // outputs 88`
`System.out.println("" + (char) 88); // outputs X` -->

### Boolean

boolean; holds results of all relational operations

## Literals

- Constants that can be assigned to variables
- prefix `0x` for hex, prefix `0` for octal, no prefix for base 10, `0b` for binary
- Floating point literals are just decimal values. Can you `E` notation for exponents. Append `f` for 32 bit precision.
- Character Literals: there can be normal characters and escape sequences (like `\n`) and octal/hexadecimal representations for different characters.
- String literals: Strings are implemented as objects in Java and they are not just arrays of characters. Literals are just text in `""`.

## Variables

- Init variables: `<datatype> <name> = <value>`
- no concepts of global scope (no code outside classes)
- scopes defined by block boundaries (`{}`, can be class boundaries, method boundaries, or block boundaries)
- var must be init before they can be used
- vars will be re-init everytime a block is re-entered
- no variables in an inner scope can have the same name as a var in the outer scope
```java
public class Scope {
    public static void main(String[] args) {
        int x = 10;
        if (x == 10) {
            x = 100;// this updates the outer x to 100 because the outer variables are available in
                    // this inner scope and they are still pointing to the outer scope's variables
            // int x = 10; // this statement is illegal, cannot redefine a variable
            int y = 10; // this var is only accessible in this block & not outside
        }
        System.out.println(x);
        // y += 10; // y is not available in this scope, this call is illegal
    }
}
```

## Automatic Type Conversion & Casting

- Automatic conversions only happens if:
    - the types are compatible
    - dest type > source type
- eg: int can hold a byte and no cast is required; int and float are compatible with each other
- when automatic conversion is not available (like between char & boolean), we must use a cast
