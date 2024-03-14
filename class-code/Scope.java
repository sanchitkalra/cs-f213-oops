public class Scope {
    public static void main(String[] args) {
        int x = 10;
        if (x == 10) {
            x = 100;// this updates the outer x to 100 because the outer variables are available in
                    // this inner scope and they are still pointing to the outer scope's variables
            // int x = 10; // this statement is illegal, cannot redefine a variable
        }
        System.out.println(x);
    }
}
