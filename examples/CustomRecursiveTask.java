package examples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class CustomRecursiveTask extends RecursiveTask<Integer> {
    private int[] arr;

    private static final int THRESHOLD = 20;

    public CustomRecursiveTask(int[] arr) {
        this.arr = arr;
    }

    @Override
    protected Integer compute() {
        if (arr.length > THRESHOLD) {
            return ForkJoinTask.invokeAll(createSubtasks())
                    .stream()
                    .mapToInt(ForkJoinTask::join)
                    .sum();
        } else {
            return processing(arr);
        }
    }

    private Collection<CustomRecursiveTask> createSubtasks() {
        List<CustomRecursiveTask> dividedTasks = new ArrayList<>();
        dividedTasks.add(new CustomRecursiveTask(
                Arrays.copyOfRange(arr, 0, arr.length / 2)));
        dividedTasks.add(new CustomRecursiveTask(
                Arrays.copyOfRange(arr, arr.length / 2, arr.length)));
        return dividedTasks;
    }

    // select all integers between 11 and 26, multiply each by 10, then get the sum
    private Integer processing(int[] arr) {
        return Arrays.stream(arr)
                .filter(a -> a > 10 && a < 27)
                .map(a -> a * 10)
                .sum();
    }

    public static void main(String[] args) {
        int range = 5000;
        Random rand = new Random();
        int[] input = new int[range];
        // simply generate integers between 0 and 30 to fill the input
        for (int i = 0; i < range; i++) {
            input[i] = rand.nextInt(31);
        }
        CustomRecursiveTask crt = new CustomRecursiveTask(input);
        ForkJoinPool fjPool = new ForkJoinPool();
        int sum = fjPool.invoke(crt);
        System.out.println("The sum is " + sum);

    }

}
