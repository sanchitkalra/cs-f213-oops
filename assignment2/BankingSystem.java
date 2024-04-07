package assignment2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

class BankAccount {
    long balance;

    BankAccount() {
        this.balance = 0;
    }

    long get() {
        return this.balance;
    }

    void set(long diff) throws ArithmeticException {
        if (this.balance + diff < 0) {
            throw new ArithmeticException(": Illegal mathematical operation");
        } else {
            this.balance += diff;
        }
    }
}

class Transaction {
    Thread t;
    int tid;
    BankAccount bankAccount;
    long amt;

    Transaction(int tid, BankAccount bankAccount, long amt) {
        this.tid = tid;
        this.bankAccount = bankAccount;
        this.amt = amt;
    }
}

class Withdrawal extends Transaction implements Runnable {
    Withdrawal(int tid, BankAccount bankAccount, long amt) {
        super(tid, bankAccount, amt);
        this.t = new Thread(this, "w_thread " + this.tid);
    }

    public void run() {
        try {
            this.bankAccount.set(-amt);
            System.out.println(this.bankAccount.get());
        } catch (ArithmeticException e) {
            System.out.println(e.getClass().getCanonicalName() + e.getMessage());
        }
    }
}

class Deposit extends Transaction implements Runnable {
    Deposit(int tid, BankAccount bankAccount, long amt) {
        super(tid, bankAccount, amt);
        this.t = new Thread(this, "d_thread " + this.tid);
    }

    public void run() {
        try {
            this.bankAccount.set(amt);
            System.out.println(this.bankAccount.get());
        } catch (ArithmeticException e) {
            System.out.println(e.getClass().getCanonicalName() + e.getMessage());
        }
    }
}

public class BankingSystem {
    public static void main(String[] args) {
        BankAccount account = new BankAccount();

        ArrayList<String> inputs = new ArrayList<String>();

        try (FileInputStream fis = new FileInputStream(args[0])) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            while (reader.ready()) {
                String line = reader.readLine();
                inputs.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException: Error opening the specified file");
            return;
        } catch (IOException e) {
            System.out.println("IOException: Error in IO");
            return;
        }

        Transaction tnxs[] = new Transaction[inputs.size()];

        for (int k = 0; k < inputs.size(); k++) {
            if (inputs.get(k).charAt(0) == 'd') {
                try {
                    long amt = Integer.parseInt(inputs.get(k).substring(1));
                    tnxs[k] = new Deposit(k, account, amt);
                    tnxs[k].t.start();
                    try {
                        tnxs[k].t.join();
                    } catch (InterruptedException e) {
                        System.out.println("InterruptedException: Thread interrupted");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("NumberFormatException: Error in string to number conversion");
                }
            } else {
                try {
                    long amt = Integer.parseInt(inputs.get(k).substring(1));
                    tnxs[k] = new Withdrawal(k, account, amt);
                    tnxs[k].t.start();
                    try {
                        tnxs[k].t.join();
                    } catch (InterruptedException e) {
                        System.out.println("InterruptedException: Thread interrupted");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("NumberFormatException: Error in string to number conversion");
                }
            }
        }
    }
}
