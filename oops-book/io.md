# I/O

_this doc refers to ch 13&22 of the textbook_

## Streams

- stream -> abstraction -> either produce or consume information -> linked to a physical device by Java IO system
- types of IO streams
  - byte streams: used for reading/writing binary data
  - character streams: suitable for chars, follow unicode, maybe more efficient in some cases
- Byte Streams: two key classes: InputStream and OutputStream
  - they are abstract and define key methods that other stream classes implement; two most imp methods -> read and write
- Char Streams: two key abstract classes Reader and Writer which handle unicode char streams; key methods -> read and write
- System (part of the java.lang package, which is imported by default) contains 3 pre-defined stream vars:
  - System.in (obj of InputStream) -> std i/p stream -> keyboard by default
  - System.out (obj of PrintStream) -> std o/p stream -> console by default
  - System.err (obj of PrintStream) -> std err stream -> console by default
- default streams can be redirected to any valid output device

## Reading Console Input

- char oriented stream preferred over byte streams for console input
- console input using System.in -> wrap System.in inside a BufferedReader
- old way: `BufferedReader br = new BufferedReader(new InputStreamReader(System.in));` -> br is a char based stream
- now since JDK 17, it is recommended to specify the charset of the input stream as well, the constructor changes to `InputStreamReader(InputStream inputStream, Charset charset)` -> this charset is usually taken as the charset of the console (returned by the `charset()` method of the Console class)

```java
Console con = System.console(); // get the console
if(con == null) return; // if no console present, return

BufferedReader br = new BufferedReader(new InputStreamReader(System.in, con.charset()));
```

### Reading Characters

- read chars using the `int read() throws IOException` method
  - reads char from ip streams and return as char; return -1 when attempt to read from end of stream
- System.in is buffered by default => no input is passed to the prog until the enter key is pressed

### Reading Strings

- we can use the method `String readLine() throws IOException`

```java
BufferedReader br = new BufferedReader(new InputStreamReader(System.in, System.console().charset()));

String ip = br.readLine();
```

### Console Output

- `print()`, `println()` methods of the PrintStream class (obj referenced by System.out) (note that System.out is a byte stream)
- since PrintStream is a derivative of OutputStream, we can also use the low level method `void write(int byteval)`. it writes the lower 8 bits (1 byte) specified by the byteval

#### PrintWriter

- character based class
- key constructor `PrintWriter(OutputStream outputStream, boolean flushingOn)`
  - outputStream -> obj of type OutputStream
  - flusingOn -> if the o/p stream should be flushed everytime println or a similar method is called
- this class supports `print` and `println` methods and if the arg type is not of type string it implicitly calls the object's toString method

```java
PrintWriter pw = new PrintWriter(System.out, true);

pw.println("hello world!");
```

## Reading/Writing Files

### Reading Files

- most imp stream classes -> `FileInputStream` and `FileOutputStream`
  - constructor: `FileInputStream(String filename) throws FileNotFoundException`
  - constructor: `FileOutputStream(String filename) throws FileNotFoundException`
- for output streams, even if the file can't be opened or created the `FileNotFoundException` (subclass of IOException) is thrown
- when an output file is opened, any preexisting file with the same name is destroyed
- when done working with a file, close it with the `void close() throws IOException` method defined on both the FileInputStream and FileOutputStream classes
- The close( ) method is specified by the AutoCloseable interface in java.lang. AutoCloseable is inherited by the Closeable interface in java.io. Both interfaces are implemented by the stream classes, including FileInputStream and FileOutputStream.
- file can be closed automatically when no longer needed by using the `try-with-resources` syntax
- to read from a file we can used the `int read() throws IOException` method defined by the FileInputStream
  - valid read -> return single byte as int; invalid read (read at end of stream) -> return -1

```java
FileInputStream fin;

// create a new input stream
try {
    fin = new FileInputStream(fileName);
} catch (FileNotFoundException e) {
    // file isn't present, handle error
    System.out.println("error");
    return;
}

// attempt to read the file
try {
    int i;
    do {
        i = fin.read();
        if (i != -1) {
            // do something with this
            System.out.println((char) i);
        }
    } while (i != -1); // i == -1 => end of stream
} catch (IOException e) {
    // handle error
}

// attempt to close the file; free up resources
try {
    fin.close();
} catch (IOException e) {
    // handle errors; unable to close file
}
```

- we can also use the `finally` keyword to better handle the closure of the file stream -> no matter what way the try block terminates, the file will be closed -> adv: the file is closed by the finally block even if the access to the file terminates because of some non-IOException

```java
...// assume file stream is opened, and fin's object is available

try {
    // do something with the file stream
} catch (IOException e) {
    // handle error
} finally {
    // close the file here
    try {
        fin.close();
    } catch (IOException e) {
        // handle file closing errors
    }
}
```

- another way to structure this code is to write all code inside one try block, and catch all exceptions outside this one try block and have one finally block to close the file

```java
int i;
FileInputStream fin = null;

try {
    // open the stream
    fin = new FileInputStream(fileName);

    // do something with the open stream
    do {
        i = fin.read();
        if (i != -1) // valid i value
    } while (i != -1);
} catch (FileNotFoundException e) {
    // catch the file not found exception from the initialiser of FIS
    // technically we could omit handling this exception explicitly because it is a subclass of IOException and only one handler for IOException is enough
} catch (IOException e) {
    // catch IOException from the fin.read() calls
} finally {
    // now close the file
    try {
        if (fin != null) // we check if fin != null because we can't close a stream that otherwise doesn't exist
            fin.close();
    } catch (IOException e) {
        // handle file closing errors
    }
}
```

### Writing Files

- to write to a file we can use `void write(int byteval) throws IOException` -> write the specified byteval (lower 8 bits of the int value) to the file

```java
// example file copying code
FileInputStream fis = null;
FileOutputStream fos = null;
int i;

try {
    fin = new FileInputStream(sourceFileName);
    fos = new FileOutputStream(destFileName);

    do {
        i = fin.read();
        if (i != -1) fos.write(i);
    } while (i != -1);
} catch (IOException e) {
    // handle IO exception
} finally {
    // close input file
    try {
        if (fin != null) fin.close();
    } catch {
        // handle file closing errors
    }

    // close output file
    try {
        if (fos != null) fos.close();
    } catch {
        // handle file closing errors
    }

    // two separate try blocks are used when closing the files. This ensures that both files are closed, even if the call to fin.close() throws an exception.
}
```

## Automatic Resource Management (auto close files)

- variation of the try statement
- prevents a resource not being released after it is not needed

```java
try (resource-specification) {
    // use the resource
}
```

- resource specification -> declare and init a resource -> consists of a variable declaration in which the variable is initialized with a reference to the object being managed
- when try block ends -> resource is automatically closed
- catch and finally blocks can still be used
- Beginning with JDK 9, it is also possible for the resource specification of the try to consist of a variable that has been declared and initialized earlier in the program. However, that variable must be effectively final, which means that it has not been assigned a new value after being given its initial value.
- this try-with-resources approach can be used only with resources that implement the AutoClosable interface (inherited by the Closable interface) -> both interfaces implemented by Stream classes

```java
try (FileInputStrem fin = new FileInputStream(fileName)) {
    // fin is now a variable with scope local to this block
    // resources declared in the try statement are implicitly final -> fin is final -> value cannot be reassigned
    // with JDK 10 -> var keyword is legal and automatic type inference is supported

    do {
        i = fin.read();
        if (i != -1) // do something
    } while (i != -1);

    // now while leaving the try block -> stream associated with fin is closed via an implicit call to fin.close()
} catch (FileNotFoundException e) {
    // handle file not found
} catch (IOException e) {
    // handle IO errors
}
```

- multiple resources can also be initialised in a try statement seperated by a `;`

```java
try (var fin = new FileInputStream(inputFile); var fout = new FileOutputStream(outputFile)) {
    // do something with the resources

    // once the try block ends both fin and fout will be closed with an implcit call to close
}
```

- There is one other aspect to try-with-resources that needs to be mentioned. In general, when a try block executes, it is possible that an exception inside the try block will lead to another exception that occurs when the resource is closed in a finally clause. In the case of a “normal” try statement, the original exception is lost, being preempted by the second exception. However, when using try-with-resources, the second exception is suppressed. It is not, however, lost. Instead, it is added to the list of suppressed exceptions associated with the first exception. The list of suppressed exceptions can be obtained by using the `getSuppressed()` method defined by Throwable.

## Transient & Volatile

### Trasient

- variables that are not persisted when obj is stored to a persistent storage area
- variable is essentially excluded from the serialisation process which is the process of converting an object to a stream of bytes to store it in memory or transmit over network
- useful when we need to make sure some data isn't transmitted or dealing with non serialisable objects

```java
class T {
    transient int a;
    int b;
}
```

### Volatile

- a variable that can be modified and can change unexpectedly by other parts of the program, for example, by other threads
- useful when a variable needs to only represent the most updated state of the program without a concern for syncronisation
- the compiler always uses the master copy of the variable which reflects the current state
- all threads will always see the most current value of the variable
- CPU optimisations where each thread has it's own copy of variables does not happen
- The volatile keyword also provides an atomicity guarantee for read-modify-write operations

## instanceof operator

- used to find out the type of an object at runtime as `objRef instanceof type`
  - type -> actual class type
  - if objRef is of type 'type' or can be cast into it -> return true; else false

```java
class A {}
class B extends A {}
class C extends B {}

// assume the following is the main method
psvm() {
    A a = new A();
    B b = new B();
    C c = new C();

    if (a instanceof A) // true

    if (b instanceof B) // true

    if (c instanceof C) // true

    if (b instanceof A) // true

    if (c instanceof A) // true

    if (c instanceof B) // true

    A ob;

    ob = c;

    if (ob instanceof A) // true
}
```

## Assert keyword

- create an assertion, a condition that should be true during the execution of a program as `assert condition`
- if condition is true -> no action; if false -> assertion error is thrown
- mostly used for testing
- can specify a custom error message with an assertion as `assert condition: expr` where expr should have a string representation

```java
psvm() {
    int j = 10;

    assert j < 10; // this will cause an exception with a normal AssertionError
    assert j < 10 : "j is < 10"; // this will fail and cause an exception with the error message specified in quotes along with the regular exception text message
}
```

- to enable assertions, run with the `-ea` flag
- never make assertions perform code operations because they will be disabled in production code
- conversely all assertions can be disabled with the `-da` flag
- assertions can be enabled/disabled for particular packages by using the package name/class name with the `-ea`/`-da` flag as `-ea:package`/`-da:package`

## Static Imports

- we can import static members and methods of a class without and use it's members without specifying the class name everytime

```java
// without static import

psvm() {
    double x = Math.sqrt(Math.pow(10, 2)); // have to write the Math. qualifier everytime
}
```

```java
// with static import

// import once at the top of your file as
import static java.lang.Math.sqrt;
import static java.lang.Math.pow;

// import static java.lang.Math.*; // imports all static members of the java.lang.Math class

psvm() {
    double x = sqrt(pow(10, 2)); // now don't have to write the Math. qualifier everytime
}
```

- overuse of this feature is a bad practice as importing more and mroe members into the current namespace increases the chances of namespace collisions

## Invoking overloaded constructors

- you can invoke another constructor via some other constructor
- reduces code duplication and in turn since your bytecode is smaller, code loads faster
- it is a game of balancing slightly slower object creation time (because of the function call and return ooverheads) and faster load time
- mostly useful for constructors with large amounts of code
- when using `this()`, `super()` becomes unavailable as that too should be the first line of code in a constructor

```java
class MyClass {
    int x, y;

    MyClass(int x, int y) {
        this.x = x;
        this.y = y;
    }

    MyClass(int p) {
        this(p, p); // here the constructor that matches the parameter types is called; the call to `this` should be the first statement
    }

    MyClass() {
        this(0); // this() here calls the MyClass(int) constructor which in turn calls the MyClass(int, int) constructor
    }
}
```

## File class

- describe the properties of a file, not how the file is stored or information is retrived
- not stream based
- used to obtain/manipulate the information associated with a disk file
- a directory is also treated as a file, but with a list of filenames (can be retrieved using the `list()` method)

```java
// key constructors

File(String directoryPath)
File(String directoryPath, String filename)
File(File dirObj, String filename)
File(URI uriObj)
```

- non-obvious methods
  - `isFile()` -> return true if called on a file, and false is called on a directory, or special files like device drivers, pipes
  - `isAbsolute()` -> true if path is absolute, false if relative
- other methods
  - `boolean renameTo(File newName)`
  - `boolean delete()` -> if called on a dir, only executes if dir is empty
- convert to Path (part of NIO) using the `Path toPath()` method
- this class implements the `Comparable` interface to `compareTo()` method is available

### Directories

- a special file that contains a list of other files and directories
- isDirectory returns true; can call `String[] list()` to get list of files and dirs inside
- we can filter the list of file returned by using `String[] list(FilenameFilter FFObj)` where FFObj is an instance of a class that implements the FilenameFilter interface (defines a method `boolean accept(File directory, String filename)` which is called for every file in a list, true => included in list)

```java
// only list files with a specific extension

public class Ext implements FilenameFilter {
    String ext;
    public Ext(String ext) {
        this.ext = ext;
    }

    // this method of the interface we need to implement
    public boolean accept(File dir, String name) {
        return name.endsWith(this.ext);
    }
}

// can use it as

psvm() {
    File f1 = new File(dirName);
    FilenameFilter filter = new Ext("pdf");
    String[] files = f1.list(filter); // list of files that have the pdf extension
}
```

- alternatively we can use the following methods to retrieve the list of files as an array of File objects rather than String

```java
File[] listFiles()
File[] listFiles(FilenameFilter FFObj)
File[] listFiles(FileFilter FObj) // returns files that satify FileFilter which has just one method to be implemented `boolean accept(File path)`
```

- new dirs can be created using the `mkdir()` (fails if the entrie path doesn't exist yet), and `mkdirs()` (works even if the entire path doesn't exist yet) methods

## AutoCloseable, Closeable, and Flushable Interfaces

- Closable and Flushable are defined in java.io and AutoClosable in java.lang
- AutoClosable used for try-with-resources
  - defines only the `void close() throws Exception`
- Closable extends AutoClosable
- Flusable can force buffered output to be written to the stream to which the object is attached
  - defines `void flush() throws IOException`
  - buffered output physically written to the underlying device

## IOException

- IO error -> IOException
- FileNotFoundException subclass of IOException

## Ways to close streams

- stream must be closed when not needed
- explicitly call `close()` inside the finally block or an implicit call to close via the try with resources approach

## Stream Classes

- four key abstract classes: InputStream, OutputStream, Reader, Writer
  - first two for byte streams and second two for char streams

## Byte Streams

- useful for any kind of object, including binary data

### InputStream

- streaming byte input
- implements Closable and AutoClosable

### OutputStream

- streaming byte output
- implements AutoClosable, Closable, and Flushable interfaces

### FileInputStream

- creates an InputStream to read bytes from files

```java
// common constructors

FileInputStream(String filepath); // full path of file
FileInputStream(File fileObj); // file obj that describes the file
```

- throws FileNotFoundException
- see pg 759 of textbook PDF for a list of fns
- creating a stream also opens the stream for reading
- mark and reset methods are not overridden, and a call to reset results in IOException

```java
psvm() {
    int size;

    try (FileInputStream f = new FileInputStream(fileName)) {
        size = f.available(); // total number of bytes in the file available

        char x = (char) f.read(); // first byte of file; int typecasted to char

        byte[] b = new byte[10];
        if (f.read(b) != 10) {
            // check if 10 bytes were read into the byte array b
        } else {
            // convert to String
            String str = new String(b, 0, 10);
        }

        f.skip(5); // skip 5 bytes

        f.available(); // finally see how many bytes are still available to read
    }
}
```

### FileOutputStream

- creates OutputStream
- write bytes to file
- implements Closable, AutoClosable, and Flushable

```java
// common constructors
// they can throw a FileNotFoundException
FileOutputStream(String filePath)
FileOutputStream(File fileObj)

// if append true -> open in append mode
FileOutputStream(String filePath, boolean append)
FileOutputStream(File fileObj, boolean append)
```

- does not depend on the file already existing, if it doesn't exist, it will be created
- if you attempt to access a read only write, exception

```java
psvm() {
    FileOutputStream f1 = null;

    try {
        f1 = new FileOutputStream(fileName);

        String txt = "HelloWorld!";
        byte[] bytes = txt.getBytes();

        for (int k = 0; k < bytes.length; k++) {
            f1.write(bytes[k]); // write individual bytes
        }

        f1.write(bytes); // write entire byte array

        f1.write(bytes, bytes.length - bytes.length/2, bytes.length/2); // write the second half of the bytes, 2nd arg -> offset from 0 of byte array, 3rd arg -> number of bytes to write
    } catch (IOException e) {
        // handle exception
    } finally {
        try {
            if (f1 != null) f1.close();
        } catch (IOException e) {
            // handle file closing exception
        }
    }
}
```

### ByteArrayInputStream

- implementation of input stream that uses byte array as source

```java
// constructors
ByteArrayInputStream(byte[] array)
ByteArrayInputStream(byte[] array, int start, int numBytes)
```

- close has no effect on this
- implements mark and reset methods
  - if mark hasn't been called, reset sets the stream pointer to beginning of the stream (in this case, start of the byte array)

```java
class ByteArrayInputStreamReset {
    public static void main(String[] args) {
        String tmp = "abc";
        byte[] b = tmp.getBytes();

        ByteArrayInputStream in = new ByteArrayInputStream(b);
        for (int i=0; i<2; i++) {
            int c;
            while ((c = in.read()) != -1) {
                if (i == 0) {
                    System.out.print((char) c);
                } else {
                    System.out.print(Character.toUpperCase((char) c));
                }
            }
            System.out.println();
            in.reset();
        }
    }
}
```

### ByteArrayOutputStream

- impl of OutputStream
- destination is byte array

```java
// common constructors
ByteArrayOutputStream() // a buffer of 32 bytes is created
ByteArrayOutputStream(int numBytes) // size = numBytes
```

- buffer in a protected buf field
- buffer size is increased automatically as needed
- the number of _actual bytes stored_ is held in a protected count field
- close() has no meaning, although calling it produces no error

```java
psvm() {
    ByteArrayOutputStream f = new ByteArrayOutputStream(); // buffer of default size 32
    String src = "Hello World!";
    byte[] buf = src.getBytes();

    try {
        f.write(buf);
    } catch (IOException e) {
        // handle excp
    }

    // print elements line by line
    byte[] b = f.toByteArray();
    for (int k = 0; k < b.length; k++)
        System.out.println((char) b[k]);

    System.out.println(f.toString()); // directly convert to string

    // write to another output stream
    try (FileOutputStream f2 = new FileOutputStream(fileName)) {
        f.writeTo(f2);
    } catch (IOException e) {
        // handle excp
    }
}
```

## Serialisation

- write state of obj to a byte stream
- to be serialisation, class must implement the `Serializable` interface
- the interface has no members, only used to mark a class as serializable
- class serializable -> all subclass serializable
- transient and static variables are not saved

## ObjectOutput

- interface extends the DataOutput and AutoClosable interfaces
- `writeObject(Object obj)` used to serialise an object

### ObjectOutputStream

- extends OutputStream and implements ObjectOutput
- constructor `ObjectOutputStream(OutputStream outStream) throws IOException`
  - serialized objects written to outStream
- closing ObjectOutputStream closes the outStream automatically

### ObjectInput

- extends DataInput and AutoClosable
- `readObject()` -> used to deserialize an object -> can throw a ClassNotFoundException

### ObjectInputStream

- extends InputStream and implements ObjectInput interface
- constructor `ObjectInputStream(InputStream inStream) throws IOException`
  - inStream -> stream from where serialised objects should be read
- closing ObjectInputStream automatically closes the inStream

```java
class MyClass implements Serializable {
    String s;
    int i;
    Double d;

    public MyClass(String s, int i, double d) {
        this.s = s;
        this.i = i;
        this.d = d;
    }

    public String toString() {
        return "s=" + s + "; i=" + i + "; d=" + d;
    }
}

psvm() {

    // write object to output stream
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(outputFile))) {
        MyClass c1 = new MyClass("string", 1, 2.0);

        oos.writeObject(c1);
    } catch (IOException e) {
        // handle exceptions
    }

    // read object from input stream
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(outputFile))) {
        // optionally we can add a deserialization filter as follows by using the Config nested class' createFilter method which expects a string pattern
        ObjectInputFilter filter = ObjectInputFilter.Config.createFilter("MyClass;!*");
        ois.setObjectInputFilter(filter);

        MyClass c2 = (MyClass) ois.readObject();
    } catch (Exception e) {
        // handle excp
    }
}
```
