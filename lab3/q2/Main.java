package lab3.q2;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(0);
        User user1 = new User(account, "Tan");
        User user2 = new User(account, "Wong");

        Thread t1 = new Thread(user1);
        Thread t2 = new Thread(user2);

        t1.start();
        t2.start();
    }

    static class BankAccount {
        private int balance;

        public BankAccount(int balance) {
            this.balance = balance;
        }

        public int getBalance() {
            return balance;
        }

        public synchronized void deposit(String name, int amount) {
            balance += amount;
            System.out.printf("%s Deposited " + amount + ". Current balance: " + balance + "\n", name);
        }

        public synchronized void withdraw(String name, int amount) {
            if (balance < amount) {
                System.out.println("Insufficient balance.");
                return;
            }
            balance -= amount;
            System.out.printf("%s withdrawn " + amount + ". Current balance: " + balance + "\n", name);
        }
    }

    static class User implements Runnable {
        private BankAccount account;
        private String name;

        public User(BankAccount account, String name) {
            this.account = account;
            this.name = name;
        }

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                Random r = new Random();
                account.deposit(this.name, r.nextInt(50) + 51);
                account.withdraw(this.name, r.nextInt(30) + 51);
            }
        }
    }
}
