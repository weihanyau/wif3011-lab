import java.util.Random;

public class BankingQueueAtomic {
    public static volatile int currentNumber = 0;

    public static void main(String[] args) throws InterruptedException {
        Random rand = new Random();
        int customerNumber = rand.nextInt(10) + 1;

        CustomerInLine customer = new CustomerInLine(customerNumber);
        CallingQueue callingQueue = new CallingQueue();

        customer.start();
        callingQueue.start();
    }
}

class CallingQueue extends Thread {
    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.println("Calling for the customer #" + i);
            BankingQueueAtomic.currentNumber = i;
            try {
                Thread.sleep(200);
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
        while (true) {
            if (customerNumber == BankingQueueAtomic.currentNumber) {
                System.out.println("Great, finally #" + customerNumber + " was called, now it is my turn");
                break;
            }
        }
    }
}