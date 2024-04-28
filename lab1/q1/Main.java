package lab1.q1;

public class Main {
    public static void main(String[] args) {
        PrintChar printA = new PrintChar('a', 10);
        PrintChar printB = new PrintChar('b', 10);
        PrintNum printNum = new PrintNum(20);

        Thread thread1 = new Thread(printA);
        Thread thread2 = new Thread(printB);
        Thread thread3 = new Thread(printNum);

        thread1.start();
        thread2.start();
        thread3.start();
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
            while (count < times) {
                System.out.println(charToPrint);
                count++;
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
            while (count <= targetNum) {
                System.out.println(count);
                count++;
            }
        }

        @Override
        public void run() {
            print();
        }
    }
}
