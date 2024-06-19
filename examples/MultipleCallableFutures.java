/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examples;

/**
 *
 * @author Chiew
 */
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MultipleCallableFutures {

    private static final int NTHREDS = 10;

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);
        List<MyCallable> taskList = new ArrayList<>();
        for (int i = 0; i < 20000; i++) {
            MyCallable worker = new MyCallable();
            taskList.add(worker);
        }
        List<Future<Long>> resultList = null;
        try {
            resultList = executor.invokeAll(taskList);
        } catch (InterruptedException e) {
        }

        long sum = 0;
        System.out.println("Adding results from " + taskList.size() + " callable objects.");
        for (Future<Long> future : resultList) {
            try {
                sum += future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Total = " + sum);
        executor.shutdown();
    }
}
