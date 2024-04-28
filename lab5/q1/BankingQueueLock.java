package lab5.q1;

import java.util.Random;

public class BankingQueueLock {
    public static int currentNumber = 1;
    public static final Object lock = new Object();

    public static void main(String[] args) {
        Random rand = new Random();
        int customerNumber = rand.nextInt(10) + 1;
        System.out.println("Randomed customer number: " + customerNumber);

        CallingQueue callingQueue = new CallingQueue();
        CustomerInLine customer = new CustomerInLine(customerNumber);

        // Start the threads
        customer.start();
        callingQueue.start();
    }
}

class CallingQueue extends Thread {
    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            synchronized (BankingQueueLock.lock) {
                BankingQueueLock.currentNumber = i;
                System.out.println("Calling for the customer #" + i);
                BankingQueueLock.lock.notify();
                try {
                    BankingQueueLock.lock.wait();
                    Thread.sleep(200); // Pause for 200ms
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class CustomerInLine extends Thread {
    private int customerNumber;

    public CustomerInLine(int customerNumber) {
        this.customerNumber = customerNumber;
    }

    @Override
    public void run() {
        synchronized (BankingQueueLock.lock) {
            while (true) {
                try {

                    BankingQueueLock.lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (BankingQueueLock.currentNumber == customerNumber) {
                    System.out.println("Great, finally #" + customerNumber + " was called, now it is my turn");
                }
                BankingQueueLock.lock.notify();
                if (BankingQueueLock.currentNumber == 10) {
                    break;
                }
            }
        }
    }
}