class Rectangle {
    private int length;
    private int breadth;

    public int getArea() {
        return length * breadth;
    }

    public int getPeri() {
        return 2 * (length + breadth);
    }

    public void setLength(int len) {
        this.length = len;
    }

    public void setBreadth(int breadth) {
        this.breadth = breadth;
    }

    public int getLength() {
        return this.length;
    }

    public int getBreadth() {
        return this.breadth;
    }
}

public class Enapsulation {
    public static void main(String[] args) {
        Rectangle r = new Rectangle();

        r.setLength(10);
        r.setBreadth(20);

        System.out.println(
                "Area = " + Integer.toString(r.getArea()) + " and perimeter = " + Integer.toString(r.getPeri()));
    }
}