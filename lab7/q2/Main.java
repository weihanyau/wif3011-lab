package lab7.q2;

import java.util.concurrent.CompletableFuture;

public class Main {
    public static void main(String[] args) {
        System.out.println("Calculating Fibonacci(30)");
        long startTime = System.currentTimeMillis();
        int fibonacci30 = fibonacci(30);
        long endTime = System.currentTimeMillis();
        System.out.println("Fibonacci(30) = " + fibonacci30);
        System.out.printf("Start at %d\tFinish at %d\tCalculation time for Fibonacci(%d) = %d milliseconds\n", startTime, endTime, 30, endTime - startTime);
        System.out.println("Calculating Fibonacci(30)");
        long startTime2 = System.currentTimeMillis();
        int fibonacci31 = fibonacci(31);
        long endTime2 = System.currentTimeMillis();
        System.out.println("Fibonacci(31) = " + fibonacci31);
        System.out.printf("Start at %d\tFinish at %d\tCalculation time for Fibonacci(%d) = %d milliseconds\n", startTime2, endTime2, 31, endTime2 - startTime2);
        System.out.printf("Earlier Start: %d Later Finish %d\n", startTime, endTime2);
        System.out.printf("\tTotal calculation time = %d milliseconds\n", endTime2 - startTime);

        System.out.println("Calculating Fibonacci(30) asynchronously");
        long startTime3 = System.currentTimeMillis();
        CompletableFuture<Integer> fibonacci30Async = fibonacciAsync(30);
        long endTime3 = System.currentTimeMillis();
        System.out.println("Fibonacci(30) = " + fibonacci30Async.join());
        System.out.printf("Start at %d\tFinish at %d\tCalculation time for Fibonacci(%d) = %d milliseconds\n", startTime3, endTime3, 30, endTime3 - startTime3);
        System.out.println("Calculating Fibonacci(31) asynchronously");
        long startTime4 = System.currentTimeMillis();
        CompletableFuture<Integer> fibonacci31Async = fibonacciAsync(31);
        long endTime4 = System.currentTimeMillis();
        System.out.println("Fibonacci(31) = " + fibonacci31Async.join());
        System.out.printf("Start at %d\tFinish at %d\tCalculation time for Fibonacci(%d) = %d milliseconds\n", startTime4, endTime4, 31, endTime4 - startTime4);
        System.out.printf("Earlier Start: %d Later Finish %d\n", startTime3, endTime4);
        System.out.printf("\tTotal calculation time = %d milliseconds\n", endTime4 - startTime3);
    }

    public static int fibonacci(int n) {
        if (n <= 1) {
            return n;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    public static CompletableFuture<Integer> fibonacciAsync(int n) {
        if (n <= 1) {
            return CompletableFuture.completedFuture(n);
        }
        CompletableFuture<Integer> n1 = fibonacciAsync(n - 1);
        CompletableFuture<Integer> n2 = fibonacciAsync(n - 2);
        return n1.thenCombineAsync(n2, Integer::sum);
    }
}