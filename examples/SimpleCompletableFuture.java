/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examples;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author Chiew Thiam Kian
 */
public class SimpleCompletableFuture {

    public static void main(String[] args) {
        long started = System.currentTimeMillis();
        try {
            //a simple asyn task
            Future<String> future = calculateAsync();
            String hello = future.get();
            System.out.println(hello + " World!");

            //cancel an asyn task
            /*
           future = calculateAsyncWithCancellation();
           hello = future.get();
           System.out.println(hello + " World!");
             */
        } catch (InterruptedException | ExecutionException ex) {
            // Exceptions from the future should be handled here
        }
        System.out.println("CompletableFuture took " + (System.currentTimeMillis() - started) + " milliseconds");

    }

    public static Future<String> calculateAsync() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        CompletableFuture<String> completableFuture
                = new CompletableFuture<>();

        executorService.submit(() -> {
            Thread.sleep(500);
            completableFuture.complete("Hello");
            return null;
        });
        executorService.shutdown();
        return completableFuture;

    }

    public static Future<String> calculateAsyncWithCancellation() throws InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            Thread.sleep(500);
            completableFuture.cancel(false);
            return null;
        });
        return completableFuture;
    }

}
