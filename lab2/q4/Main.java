package lab2.q4;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random r = new Random();
        int[] numbers = new int[1000000];

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = r.nextInt(50000) + 1;
        }

        Timer timerSequential = new Timer();
        Timer timerConcurrentTwoThreads = new Timer();
        Timer timerConcurrentFourThreads = new Timer();

        // Sequential
        System.out.println("Sequential:");
        timerSequential.markStart();
        System.out.println("The largest number is: " + LargestNumberSequential.findLargestNumber(numbers));
        timerSequential.markEnd();
        System.out.println("Time taken: " + timerSequential.getTime() + " ms\n");

        // Concurrent two threads
        System.out.println("Concurrent 2 Threads:");
        int[] twoThreadsResult = new int[2];
        Thread t1 = new Thread(new LargestNumberConcurrent(numbers, 0, numbers.length / 2, twoThreadsResult, 0));
        Thread t2 = new Thread(
                new LargestNumberConcurrent(numbers, numbers.length / 2 + 1, numbers.length, twoThreadsResult, 1));

        timerConcurrentTwoThreads.markStart();
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
            System.out.println("The largest number is " + Math.max(twoThreadsResult[0], twoThreadsResult[1]));
            timerConcurrentTwoThreads.markEnd();
            System.out.println("Time taken: " + timerConcurrentTwoThreads.getTime() + " ms\n");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Concurrent four threads
        System.out.println("Concurrent 4 Threads:");
        int[] fourThreadsResult = new int[4];
        Thread t3 = new Thread(new LargestNumberConcurrent(numbers, 0, numbers.length / 4, fourThreadsResult, 0));
        Thread t4 = new Thread(
                new LargestNumberConcurrent(numbers, numbers.length / 4 + 1, numbers.length / 2, fourThreadsResult, 1));
        Thread t5 = new Thread(
                new LargestNumberConcurrent(numbers, numbers.length / 2 + 1, numbers.length * 3 / 4, fourThreadsResult,
                        2));
        Thread t6 = new Thread(
                new LargestNumberConcurrent(numbers, numbers.length / 3 / 4 + 1, numbers.length,
                        fourThreadsResult,
                        3));

        timerConcurrentFourThreads.markStart();
        t3.start();
        t4.start();
        t5.start();
        t6.start();

        try {
            t3.join();
            t4.join();
            t5.join();
            t6.join();
            int fourThreadsMax = fourThreadsResult[0];
            for (int i = 1; i < fourThreadsResult.length; i++) {
                fourThreadsMax = Math.max(fourThreadsMax, fourThreadsResult[i]);
            }
            System.out.println("The largest number is " + fourThreadsMax);
            timerConcurrentFourThreads.markEnd();
            System.out.println("Time taken: " + timerConcurrentFourThreads.getTime() + " ms");
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
        private int[] threadResult;

        public LargestNumberConcurrent(int[] array, int start, int end, int[] threadResult, int threadNum) {
            this.array = array;
            this.start = start;
            this.end = end;
            this.threadNum = threadNum;
            this.threadResult = threadResult;
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
