/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examples;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 *
 * @author Chiew Thiam Kian
 */
public class chain {

    public static void main(String[] args) {
        CompletableFuture<String> completableFuture
                = CompletableFuture.supplyAsync(() -> "Hello");

        CompletableFuture<String> future = completableFuture
                .thenApply(s -> s + " World");
        try {

            System.out.println("Hello World vs " + future.get());
        } catch (InterruptedException | ExecutionException ex) {

        }

    }

}
