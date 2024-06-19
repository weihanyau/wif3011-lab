package lab10.q3;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;

public class Main {
    public static void main(String[] args) {
        // Sequential
        long startTime = System.currentTimeMillis();
        long sumOfFactorial = LongStream.rangeClosed(1, 20).reduce(0, (result, n) -> result + factorial(n));
        long endTime = System.currentTimeMillis();
        System.out.println("Sequential sum of factorials form 1 to 20 is: " + sumOfFactorial);
        System.out.println("Sequential execution time: " + (endTime - startTime) + "ms");

        // RecursiveTask
        FactorialRecursiveTask.run();

        // RecursiveAction
        FactorialRecursiveAction.run();
    }

    public static long factorial(long n) {
        if (n == 0)
            return 1;
        return n * factorial(n - 1);
    }

    static class FactorialRecursiveTask {
        public static void run() {
            long startTime = System.currentTimeMillis();
            ForkJoinPool pool = new ForkJoinPool();
            SumOfFactorials task = new SumOfFactorials(1, 20);
            long result = pool.invoke(task);
            long endTime = System.currentTimeMillis();
            System.out.println("RecursiveTask sum of factorials from 1 to 20 is: " + result);
            System.out.println("RecursiveTask execution time: " + (endTime - startTime) + "ms");
        }

        static class SumOfFactorials extends RecursiveTask<Long> {
            private static final int THRESHOLD = 2;
            private int start;
            private int end;

            public SumOfFactorials(int start, int end) {
                this.start = start;
                this.end = end;
            }

            @Override
            protected Long compute() {
                if (end - start <= THRESHOLD) {
                    long sum = 0;
                    for (int i = start; i <= end; i++) {
                        sum += factorial(i);
                    }
                    return sum;
                } else {
                    // Split the task into smaller tasks
                    int mid = (start + end) / 2;
                    SumOfFactorials leftTask = new SumOfFactorials(start, mid);
                    SumOfFactorials rightTask = new SumOfFactorials(mid + 1, end);

                    leftTask.fork();
                    long rightResult = rightTask.compute();
                    long leftResult = leftTask.join();

                    return leftResult + rightResult;
                }
            }

            private long factorial(int n) {
                if (n == 0)
                    return 1;

                return n * factorial(n - 1);
            }
        }
    }
    
    static class FactorialRecursiveAction {
        private static AtomicLong totalSum = new AtomicLong(0);

        public static void run() {
            long startTime = System.currentTimeMillis();
            ForkJoinPool pool = new ForkJoinPool();
            SumOfFactorials task = new SumOfFactorials(1, 20);
            pool.invoke(task);
            System.out.println("RecursiveAction sum of factorials from 1 to 20 is: " + totalSum);
            long endTime = System.currentTimeMillis();
            System.out.println("RecursiveAction execution time: " + (endTime - startTime) + "ms");
        }

        static class SumOfFactorials extends RecursiveAction {
            private static final int THRESHOLD = 2;
            private int start;
            private int end;

            public SumOfFactorials(int start, int end) {
                this.start = start;
                this.end = end;
            }

            @Override
            protected void compute() {
                if (end - start <= THRESHOLD) {
                    long sum = 0;
                    for (int i = start; i <= end; i++) {
                        sum += factorial(i);
                    }
                    totalSum.addAndGet(sum);
                    return;
                } else {
                    // Split the task into smaller tasks
                    int mid = (start + end) / 2;
                    SumOfFactorials leftTask = new SumOfFactorials(start, mid);
                    SumOfFactorials rightTask = new SumOfFactorials(mid + 1, end);

                    leftTask.fork();
                    rightTask.compute();
                    leftTask.join();
                }
            }
        }
    }
}
