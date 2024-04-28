package lab2.q2;

import java.util.Random;

public class Main {
    private static int[] threadResult = new int[2];

    public static void main(String[] args) {
        Random r = new Random();
        int[] numbers = new int[1000000];

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = r.nextInt(50000) + 1;
        }

        Thread t1 = new Thread(new LargestNumberConcurrent(numbers, 0, numbers.length / 2, 0));
        Thread t2 = new Thread(new LargestNumberConcurrent(numbers, numbers.length / 2 + 1, numbers.length, 1));

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
            System.out.println("The largest number is " + Math.max(threadResult[0], threadResult[1]));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    static class LargestNumberConcurrent implements Runnable {
        private int[] array;
        private int start;
        private int end;
        private int threadNum;

        public LargestNumberConcurrent(int[] array, int start, int end, int threadNum) {
            this.array = array;
            this.start = start;
            this.end = end;
            this.threadNum = threadNum;
        }

        private int findLargestNumber() {
            int max = array[start];

            for (int i = start + 1; i < end; i++) {
                max = Math.max(max, array[i]);
            }

            return max;
        }

        @Override
        public void run() {
            int result = findLargestNumber();
            threadResult[threadNum] = result;
        }
    }
}
