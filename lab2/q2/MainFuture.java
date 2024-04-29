package lab2.q2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainFuture {
    public static void main(String[] args) {
        Random r = new Random();
        int[] numbers = new int[1000000];

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = r.nextInt(50000) + 1;
        }

        ExecutorService executor = Executors.newFixedThreadPool(2);

        List<Callable<Integer>> tasks = new ArrayList<>();
        tasks.add(new LargestNumberConcurrent(numbers, 0, numbers.length / 2));
        tasks.add(new LargestNumberConcurrent(numbers, numbers.length / 2 + 1, numbers.length));

        List<Integer> maxList = new ArrayList<>();

        try {
            List<Future<Integer>> result = executor.invokeAll(tasks);
            for (Future<Integer> future : result) {
                maxList.add(future.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        executor.shutdown();

        System.out.println("The largest number is " + Math.max(maxList.get(0), maxList.get(1)));
    }

    static class LargestNumberConcurrent implements Callable<Integer> {
        private int[] array;
        private int start;
        private int end;

        public LargestNumberConcurrent(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        private int findLargestNumber() {
            int max = array[start];

            for (int i = start + 1; i < end; i++) {
                max = Math.max(max, array[i]);
            }

            return max;
        }

        @Override
        public Integer call() throws Exception {
            int result = findLargestNumber();
            return result;
        }
    }
}
