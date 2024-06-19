package lab10.q1;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Main {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        SumOfFactorials task = new SumOfFactorials(1, 20);
        long result = pool.invoke(task);
        System.out.println("Sum of factorials from 1 to 20 is: " + result);
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
