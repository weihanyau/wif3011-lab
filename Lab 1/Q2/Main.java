public class Main {
    private static boolean isCharDone = false;
    private static boolean isNumDone = false;
    private static final Object lock = new Object();

    public static void main(String[] args) {
        PrintChar printA = new PrintChar('a', 20);
        PrintNum printNum = new PrintNum(10);

        Thread thread1 = new Thread(printA);
        Thread thread2 = new Thread(printNum);

        thread2.start();
        thread1.start();
    }

    static class PrintChar implements Runnable {
        private char charToPrint;
        private int times;
        private int count = 0;

        public PrintChar(char c, int t) {
            charToPrint = c;
            times = t;
        }

        public void print() {
            synchronized (lock) {
                while (count < times) {
                    System.out.println(charToPrint);
                    count++;
                    lock.notify();
                    try {
                        if (!isNumDone) {
                            lock.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                isCharDone = true;
                lock.notify();
            }
        }

        @Override
        public void run() {
            print();
        }
    }

    static class PrintNum implements Runnable {
        private int targetNum;
        private int count = 1;

        public PrintNum(int targetNum) {
            this.targetNum = targetNum;
        }

        public void print() {
            synchronized (lock) {
                while (count <= targetNum) {
                    try {
                        if (!isCharDone) {
                            lock.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(count);
                    count++;
                    lock.notify();
                }
                isNumDone = true;
                lock.notify();
            }
        }

        @Override
        public void run() {
            print();
        }
    }
}
