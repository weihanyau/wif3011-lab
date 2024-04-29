package examples;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AccountWithSyncUsingLock {

    private static Account account = new Account();

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();

        // Create and launch 100 threads
        for (int i = 0; i < 100; i++) {
            executor.execute(new AddAPennyTask());
        }

        executor.shutdown();

        // Wait until all tasks are finished
        while (!executor.isTerminated()) {
        }

        System.out.println("What is balance? " + account.getBalance());
    }

    // A thread for adding a penny to the account
    public static class AddAPennyTask implements Runnable {

        public void run() {
            account.deposit(1);
        }
    }

    /*
     * ReentrantLock is a concrete implementation of Lock for creating mutually
     * exclusive locks. You can create a lock with the specified fairness policy.
     * True fairness policies guarantee that the longest-waiting thread will obtain
     * the lock first. False fairness policies grant a lock to a waiting thread
     * arbitrarily. Programs using fair locks accessed by many threads may have
     * poorer overall performance than those using the default setting, but they
     * have smaller variances in times to obtain locks and prevent starvation.
     */
    // An inner class for Account
    public static class Account {

        private static Lock lock = new ReentrantLock(); // Create a lock
        private int balance = 0;

        public int getBalance() {
            return balance;
        }

        public void deposit(int amount) {
            lock.lock(); // Acquire the lock

            try {
                int newBalance = balance + amount;
                Thread.sleep(5);
                balance = newBalance;
            } catch (InterruptedException ex) {
            } finally {
                lock.unlock(); // Release the lock
            }
        }
    }

}
