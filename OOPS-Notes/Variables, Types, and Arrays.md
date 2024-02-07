Java is strongly typed. Each variable and expression must have a strictly defined type. Each assignment is type checked and automatic typecasting (coercing/conversion) doesn't happen.
# Primitive datatypes
They rep singular values. Not implemented as obj for efficiency reasons and have strict ranges so that compatibility is maintained across different systems (16 bit vs 64 bit systems and also backward compatibility).
### Integers
byte, short, int, long
### Floating point numbers
float (32 bit) (floats declared by default are 64 bit; to change them to 32 bit we prefix `f` ), double (64 bit)
`float f = 3.14; // this is 64 bit`
`float f = f3.14; // this is 32 bit`
### Characters
char (16 bit and compatible with 16 bit unicode)
`char ch1 = 88; System.out.println("" + ch1); // outputs X`
`System.out.println("" + 88); // outputs 88`
`System.out.println("" + (char) 88); // outputs X`
### Boolean
boolean
# Literals
- Constants that can be assigned to variables
- prefix `0x` for hex, prefix `0` for octal, no prefix for int, `0b` for binary
### String Literals
