import java.io.IOException;
import java.util.Scanner;

class DivisionException extends ArithmeticException {
    public DivisionException(String msg) {
        super(msg);
    }
}

public class Main {

    private static void divideByNum() throws IOException {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        try {
            if (n == 0) {
                throw new DivisionException("Custom Exception: Division by zero");
            } else if (n < 0) {
                throw new DivisionException("Custom Exception: Division by negative number");
            } else if (n > 100) {
                throw new DivisionException("Custom Exception: Division by number greater than 100");
            } else {
                int result = 100 * n;
                System.out.println(result);
            }
        } catch (DivisionException exp) {
            IOException ioExp = new IOException("Chained Exception: Wrong Input taken");
            ioExp.initCause(exp);
            throw ioExp;
        }
    }

    public static void main(String[] args) throws IOException {
        try {
            divideByNum();
        } catch (IOException exp) {
            System.out.println("Caught exception: java.io.IOException: " + exp.getMessage());
            System.out.println("Original cause: " + exp.getCause());
        }
    }
}