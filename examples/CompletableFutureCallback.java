/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examples;

/**
 *
 * @author Chiew Thiam Kian
 */
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

// thenApply is used for working with a result of the previous call.
public class CompletableFutureCallback {

    public static void main(String[] args) {
        long started = System.currentTimeMillis();

        CompletableFuture<String> data = createCompletableFuture()
                .thenApply((Integer count) -> {
                    int transformedValue = count * 10;
                    return transformedValue;
                }).thenApply(transformed -> "Finally creates a string: " + transformed);

        /*
        Do not return a value
         */
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture<Void> future = completableFuture
                .thenAccept(s -> System.out.println("Do not return a value. Computation returned: " + s));

        try {
            System.out.println(data.get());

            //do not return a value
            //  future.get();
        } catch (InterruptedException | ExecutionException e) {

        }

    }

    public static CompletableFuture<Integer> createCompletableFuture() {
        CompletableFuture<Integer> result = CompletableFuture.supplyAsync(() -> {
            try {
                // simulate long running task
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            }
            System.out.println("First returns an integer...");
            return 20;
        });
        return result;
    }
}
