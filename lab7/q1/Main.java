package lab7.q1;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class Main {
    public static void main(String[] args) {
        ArrayList<CompletableFuture<Integer>> futures = new ArrayList<>();
        for (int i = 1; i <= 111111; i += 1000) {
            CompletableFuture<Integer> maxSubDivisorCount = createCompletableFuture(i, i + 1000 -1 > 111111 ? 111111 : i + 1000 -1);
            futures.add(maxSubDivisorCount);
        }
        int maxDivisorCount = 0;
        for (CompletableFuture<Integer> future : futures) {
            try {
                maxDivisorCount = Math.max(maxDivisorCount, future.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(maxDivisorCount);
    }

    public static CompletableFuture<Integer> createCompletableFuture(int start, int end) {
        CompletableFuture<Integer> result = CompletableFuture.supplyAsync(() -> {
            int maxDivisor = 0;
            for (int i = start; i < end; i++) {
                maxDivisor = Math.max(maxDivisor, findNumberOfDivisor(i));
            }
            return maxDivisor;
        });
        return result;
    }

    public static int findNumberOfDivisor(int number){
        int count = 0;
        for (int i = 1; i <= number; i++) {
            if (number % i == 0) {
                count++;
            }
        }
        return count;
    }
}
