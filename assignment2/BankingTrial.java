import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

class BankAccount {
    private int balance;
    private final Object lock = new Object();

    public BankAccount(int balance) {
        this.balance = balance;
    }

    public void deposit(int amount) {
        synchronized (lock) {
            balance += amount;
        }
    }

    public void withdraw(int amount) throws ArithmeticException {
        synchronized (lock) {
            if (balance >= amount) {
                balance -= amount;
            } else {
		throw new ArithmeticException(": Illegal mathematical operation");
	    }
        }
    }

    public int getBalance() {
        synchronized (lock) {
            return balance;
        }
    }
}

class Transaction implements Runnable {
    private final BankAccount account;
    private final char type;
    private final int amount;
    private final List<Integer> balances;

    public Transaction(BankAccount account, char type, int amount, List<Integer> balances) {
        this.account = account;
        this.type = type;
        this.amount = amount;
        this.balances = balances;
    }

    @Override
    public void run() {
        if (type == 'w') {
            account.withdraw(amount);
        } else if (type == 'd') {
            account.deposit(amount);
        }
        balances.add(account.getBalance());
    }
}

public class BankingTrial {
    public static void main(String[] args) {
        if (args.length != 1) {
            return;
        }

        String filePath = args[0];
        BankAccount account = new BankAccount(0);
        List<Integer> balances = new ArrayList<>();

        if (isTextFile(filePath)) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("w") || line.startsWith("d")) {
                        char type = line.charAt(0);
                        String numberString = line.substring(1).trim();
                        int number = Integer.parseInt(numberString);
                        Runnable operation = new Transaction(account, type, number, balances);
                        operation.run(); 
                    }
                }

                // Print balances
                for (int balance : balances) {
                    System.out.println(balance);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } catch (ArithmeticException e) {
		System.out.println(e.getClass().getCanonicalName() + e.getMessage());
	    }
        }
    }

    private static boolean isTextFile(String filePath) {
        return filePath.matches(".*\\.txt$");
    }
}
