package examples;

/**
 *
 * @author Chiew
 */
public class NonAtomicCounter {

    static class Counter {
        private int c = 0;

        public void increment() {
            c++;
        }

        public int value() {
            return c;
        }
    }

    public static void main(final String[] arguments) throws InterruptedException {
        final Counter counter = new Counter();

        // 3000 threads
        for (int i = 0; i < 3000; i++) {

            new Thread(new Runnable() {

                public void run() {
                    counter.increment();
                }
            }).start();
        }
        Thread.sleep(60);
        // occasionally, the counter value may be less than 3000
        System.out.println("Final number (should be 3000): " + counter.value());
    }
}