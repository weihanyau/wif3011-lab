package lab2.q3;

import java.util.Random;

public class Main {
    private static int[] threadResult = new int[2];

    public static void main(String[] args) {
        Random r = new Random();
        int[] numbers = new int[1000000];

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = r.nextInt(50000) + 1;
        }

        Timer timerSequential = new Timer();
        Timer timerConcurrent = new Timer();

        // Sequential
        System.out.println("Sequential:");
        timerSequential.markStart();
        System.out.println("The largest number is: " + LargestNumberSequential.findLargestNumber(numbers));
        timerSequential.markEnd();
        System.out.println("Time taken: " + timerSequential.getTime() + " ms\n");

        // Concurrent
        System.out.println("Concurrent:");
        Thread t1 = new Thread(new LargestNumberConcurrent(numbers, 0, numbers.length / 2, 0));
        Thread t2 = new Thread(new LargestNumberConcurrent(numbers, numbers.length / 2 + 1, numbers.length, 1));

        timerConcurrent.markStart();
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
            System.out.println("The largest number is " + Math.max(threadResult[0], threadResult[1]));
            timerConcurrent.markEnd();
            System.out.println("Time taken: " + timerConcurrent.getTime() + " ms");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class LargestNumberSequential {
        public static int findLargestNumber(int[] array) {
            int max = array[0];

            for (int i = 1; i < array.length; i++) {
                max = Math.max(max, array[i]);
            }

            return max;
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

    static class Timer {
        private long startTime;
        private long endTime;

        public void markStart() {
            startTime = System.currentTimeMillis();
        }

        public void markEnd() {
            endTime = System.currentTimeMillis();
        }

        public long getTime() {
            return endTime - startTime;
        }
    }
}
