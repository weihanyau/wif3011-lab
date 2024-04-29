package examples;

public class AtomicCounter {
    private static final int range = 10000;

    static class Counter {
        // private AtomicInteger c = new AtomicInteger(0);
        private int c = 0;

        public void increment() {
            // c.getAndIncrement();
            c++;
        }

        public int value() {
            // return c.get();
            return c;
        }
    }

    public static void main(final String[] arguments) throws InterruptedException {
        final Counter counter = new Counter();

        // 3000 threads
        for (int i = 0; i < range; i++) {

            new Thread(new Runnable() {
                public void run() {
                    counter.increment();
                }
            }).start();
        }
        Thread.sleep(60);
        System.out.println("Final number (should be " + range + "): " + counter.value());
    }
}