public class CounterProgramVolatile {
    private static volatile int counter = 0;
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
                int nextCount = counter + 1;
                System.out.println("Counter incremented: " + nextCount);
                counter = nextCount;
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class CounterListener extends Thread {
        private int lastCount = counter;

        @Override
        public void run() {
            while (counter < TARGET_COUNT) {
                if (counter != lastCount) {
                    System.out.println("Counter changed: " + counter);
                    lastCount = counter;
                }
            }
            System.out.println("Counter changed: " + counter);
            System.exit(0);
        }
    }
}