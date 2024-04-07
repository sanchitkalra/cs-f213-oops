package assignment2;

public class Primes2 {
    public static void main(String[] args) {
        try {
            // Read the input from the console
            int n = Integer.parseInt(System.console().readLine());

            // Define the number of segments and the segment size
            int numSegments = Runtime.getRuntime().availableProcessors();
            int segmentSize = n / numSegments;

            // Create an array to store the sum of prime numbers and multiples for each
            // segment
            int[] primeSums = new int[numSegments];
            int[] multiplesSums = new int[numSegments];

            // Create an array of threads
            Thread[] threads = new Thread[numSegments];

            // Create and start the threads
            for (int i = 0; i < numSegments; i++) {
                final int segmentStart = i * segmentSize + 1;
                final int segmentEnd = (i == numSegments - 1) ? n : (i + 1) * segmentSize;

                threads[i] = new Thread(() -> {
                    int primeSum = 0;
                    int multiplesSum = 0;

                    for (int j = segmentStart; j <= segmentEnd; j++) {
                        if (isPrime(j)) {
                            primeSum += j;
                        }
                        if (j % 3 == 0 || j % 5 == 0 || j % 7 == 0) {
                            multiplesSum += j;
                        }
                    }

                    primeSums[i] = primeSum;
                    multiplesSums[i] = multiplesSum;
                });

                threads[i].start();
            }

            // Wait for all threads to complete execution
            for (Thread thread : threads) {
                thread.join();
            }

            // Calculate the total sum of prime numbers and multiples
            int primeSum = 0;
            int multiplesSum = 0;

            for (int i = 0; i < numSegments; i++) {
                primeSum += primeSums[i];
                multiplesSum += multiplesSums[i];
            }

            // Print the output
            System.out.println(primeSum + " " + multiplesSum);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: Please enter a valid integer");
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted: " + e.getMessage());
        }
    }

    // Helper method to check if a number is prime
    private static boolean isPrime(int number) {
        if (number < 2) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}