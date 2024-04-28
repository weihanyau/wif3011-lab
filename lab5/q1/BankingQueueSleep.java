package lab5.q1;

import java.util.Random;

public class BankingQueueSleep {
    public static void main(String[] args) {
        Random rand = new Random();
        int customerNumber = rand.nextInt(10) + 1;
        System.out.println("Randomed customer number: " + customerNumber);

        CallingQueue callingQueue = new CallingQueue();
        CustomerInLine customer = new CustomerInLine(customerNumber);

        // Start the threads
        callingQueue.start();
        customer.start();
    }
}

class CallingQueue extends Thread {
    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.println("Calling for the customer #" + i);
            try {
                Thread.sleep(200); // Pause for 200ms
            } catch (InterruptedException e) {
                e.printStackTrace();
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
        for (int i = 1; i <= 10; i++) {
            try {
                Thread.sleep(1); // Wait for the calling queue thread
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (i == customerNumber) {
                System.out.println("Great, finally #" + customerNumber + " was called, now it is my turn");
            }

            try {
                Thread.sleep(200); // Wait for the calling queue thread
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}