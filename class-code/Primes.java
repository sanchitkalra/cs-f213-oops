public class Primes {
    public static void main(String[] args) {
        int num = 10000000;
        boolean[] bool = new boolean[num];

        long start = System.nanoTime();

        for (int i = 0; i < bool.length; i++) {
            bool[i] = true;
        }
        for (int i = 2; i < Math.sqrt(num); i++) {
            if (bool[i] == true) {
                for (int j = (i * i); j < num; j = j + i) {
                    bool[j] = false;
                }
            }
        }
        System.out.println("List of prime numbers upto given number are : ");

        int count = 0;
        for (int i = 2; i < bool.length; i++) {
            if (bool[i] == true) {
                // System.out.println(i);
                count++;
            }
        }

        long end = System.nanoTime();

        System.out.println(count);
        System.out.println((end - start) / 1e6);
    }
}
