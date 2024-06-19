package lab10.q2;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicLong;

public class Main {
    private static AtomicLong totalSum = new AtomicLong(0);

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        SumOfFactorials task = new SumOfFactorials(1, 20);
        pool.invoke(task);
        System.out.println("Sum of factorials from 1 to 20 is: " + totalSum);
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

        private long factorial(int n) {
            if (n == 0)
                return 1;

            return n * factorial(n - 1);
        }
    }

}
