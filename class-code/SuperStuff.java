class Rect {
    private int width;
    private int height;

    Rect(int width, int height) {
        this.width = width;
        this.height = height;
    }

    Rect(Rect r) {
        this.height = r.height; // can access private members when in a copy constructor
        this.width = r.width;
        System.out.println(r.getClass()); // shows that Sqr type was passed if called through super
        // r.privateVar; // privateVar is a member of the subclass and even though an
        // object of that type was passed, it's members are not accessible here (only
        // for super calls)
    }

    int area() {
        return width * height;
    }
}

class Sqr extends Rect {
    private int privateVar;

    Sqr(int size) {
        super(size, size); // initialise the super class
        this.privateVar = 10;
    }

    Sqr(Sqr s) {
        super(s);
        this.privateVar = 10;
        // you are free to use another kind of constructor (of the superclass) as well
        // inside the copy constructor
        // super(10, 10); // this call is valid
        // s.height; // although you won't be able to access the super class' private
        // members stil
    }

    int area() {
        privateVar++;
        return super.area();
    }
}

public class SuperStuff {
    public static void main(String[] args) {
        Sqr square = new Sqr(10);
        System.out.println(square.area());

        Sqr s2 = new Sqr(square);
        System.out.println(s2.area());
    }
}
