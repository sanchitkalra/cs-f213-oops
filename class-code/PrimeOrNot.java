import java.util.Scanner;

class NumberTester {
    public boolean testOddOrEven(int n) {
        if (n % 2 == 0)
            return true;

        return false;
    }

    public boolean isPrime(int n) {
        int i = 2;
        while (i < n / 2) {
            if (n % i == 0)
                return false;
            i++;
        }
        return true;
    }
}

public class PrimeOrNot {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        NumberTester nt = new NumberTester();

        int n = sc.nextInt();

        System.out.println(String.format("%d is %s and is %s", n, nt.testOddOrEven(n) ? "even" : "odd",
                nt.isPrime(n) ? "prime" : "not prime"));
        char ch1 = 88;
        System.out.println("" + (char) ch1);

        sc.close();
    }
}
