import java.util.concurrent.atomic.AtomicInteger;

public class CounterProgramAtomic {
    private static final AtomicInteger counter = new AtomicInteger(0);
    private static final int TARGET_COUNT = 5000;

    public static void main(String[] args) {
        Thread incrementThread = new CounterIncrementer();
        Thread listenerThread = new CounterListener();

        incrementThread.start();
        listenerThread.start();
    }

    static class CounterIncrementer extends Thread {
        @Override
        public void run() {
            while (true) {
                int nextCount = counter.get() + 1;
                System.out.println("Counter incremented: " + nextCount);
                counter.set(nextCount);
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class CounterListener extends Thread {
        private int lastCount = counter.get();

        @Override
        public void run() {
            while (counter.get() < TARGET_COUNT) {
                if (counter.get() != lastCount) {
                    System.out.println("Counter changed: " + counter.get());
                    lastCount = counter.get();
                }
            }
            System.out.println("Counter changed: " + counter.get());
            System.exit(0);
        }
    }
}