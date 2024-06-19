/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examples;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.LongSummaryStatistics;
import java.util.Random;
import java.util.stream.LongStream;

/**
 *
 * @author Chiew Thiam Kian
 */
public class StreamComparison {

    public static void main(String[] args) {
        Random random = new Random();
        long[] values = random.longs(50000000, 1, 1001).toArray();

        // time summaryStatistics operations with sequential stream
        LongStream stream1 = Arrays.stream(values);
        Instant sequentialStart = Instant.now();
        LongSummaryStatistics results1 = stream1.summaryStatistics();
        Instant sequentialEnd = Instant.now();

        System.out.println("Calculating statistics on sequential stream");
        displayStatistics(results1);
        System.out.printf("Total time in ms: %d%n%n", Duration.between(sequentialStart, sequentialEnd).toMillis());

        // time summaryStatistics operations with parallel stream
        LongStream stream2 = Arrays.stream(values).parallel();
        Instant parallelStart = Instant.now();
        LongSummaryStatistics results2 = stream2.summaryStatistics();
        Instant parallelEnd = Instant.now();

        System.out.println("Calculating statistics on parallel stream");
        displayStatistics(results2);
        System.out.printf("Total time in ms: %d%n%n", Duration.between(parallelStart, parallelEnd).toMillis());

        // perform calculations separately
        Instant separateStart = Instant.now();
        long count = Arrays.stream(values).count();
        long sum = Arrays.stream(values).sum();
        long min = Arrays.stream(values).min().getAsLong();
        long max = Arrays.stream(values).max().getAsLong();
        double average = Arrays.stream(values).average().getAsDouble();
        Instant separateEnd = Instant.now();

        // display results
        System.out.println("Calculations performed separately");
        System.out.printf("    count: %d%n", count);
        System.out.printf("      sum: %d%n", sum);
        System.out.printf("      min: %d%n", min);
        System.out.printf("      max: %d%n", max);
        System.out.printf("  average: %f%n", average);
        System.out.printf("Total time in ms: %d%n%n", Duration.between(separateStart, separateEnd).toMillis());

    }

    private static void displayStatistics(LongSummaryStatistics stats) {
        System.out.println("Statistics");
        System.out.printf("    count: %d%n", stats.getCount());
        System.out.printf("      sum: %d%n", stats.getSum());
        System.out.printf("      max: %d%n", stats.getMin());
        System.out.printf("      max: %d%n", stats.getMax());
        System.out.printf("  average: %f%n", stats.getAverage());
    }

}