public class InferredTypes {
    public static void main(String[] args) {
        var x = new int[10]; // var lets the compiler infer the object type
        System.out.println(x);
    }
}
