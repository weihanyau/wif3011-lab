/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examples;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Chiew Thiam Kian
 */
public class StreamDemo {

    public static void main(String[] args) {

        List<String> myList = Arrays.asList("a1", "a2", "b1", "c2", "c1");

        myList
                .stream()// convert list into stream
                .filter(s -> s.startsWith("c")) // filter by predicate
                .map(String::toUpperCase) // :: = method reference
                .sorted()
                .forEach(System.out::println);

        /*
         * Arrays.asList("a1", "a2", "a3")
         * .stream()
         * .findFirst()
         * .ifPresent(System.out::println);
         */
        /*
         * IntStream.range(1, 4) //(1 inclusive, 4 exclusive)
         * .forEach(System.out::println);
         * 
         * System.out.printf("Sum of 1 through 10 is:  %d%n", IntStream.rangeClosed(1,
         * 10).sum());
         */
        /*
         * //intermediate aggregate function
         * Arrays.stream(new int[]{1, 2, 3})
         * .map(n -> 2 * n + 1)
         * .average()
         * .ifPresent(System.out::println);
         * 
         * Stream.of("a1", "a2", "a3")
         * .map(s -> s.substring(1))
         * .mapToInt(Integer::parseInt)
         * .max().ifPresent(System.out::println);
         */
        /*
         * //Collection not always needed to create a stream
         * Stream.of(1.0, 2.0, 3.0)
         * .mapToInt(Double::intValue)
         * .mapToObj(i -> "a" + i)
         * .forEach(System.out::println);
         */

        /*
         * //laziness of intermediate function
         * Stream.of("d2", "a2", "b1", "b3", "c")
         * .filter(s -> {
         * System.out.println("filter: " + s);
         * return true;
         * });
         */
        /*
         * //intermediate function with terminal function
         * Stream.of("d2", "a2", "b1", "b3", "c")
         * .filter(s -> {
         * System.out.println("filter: " + s);
         * return true;
         * })
         * .forEach(s -> System.out.println("forEach: " + s));
         */

        /*
         * //reduce the number of pipeline operations
         * Stream.of("d2", "a2", "b1", "b3", "c")
         * .map(s -> {
         * System.out.println("map: " + s);
         * return s.toUpperCase();
         * })
         * .anyMatch(s -> {
         * System.out.println("anyMatch: " + s);
         * return s.startsWith("A");
         * });
         */
        /*
         * //the importance of order
         * Stream.of("d2", "a2", "b1", "b3", "c")
         * .map(s -> {
         * System.out.println("map: " + s);
         * return s.toUpperCase();
         * })
         * .filter(s -> {
         * System.out.println("filter: " + s);
         * return s.startsWith("A");
         * })
         * .forEach(s -> System.out.println("forEach: " + s));
         */

        /*
         * //just chnage the order of map and filter
         * Stream.of("d2", "a2", "b1", "b3", "c")
         * .filter(s -> {
         * System.out.println("filter: " + s);
         * return s.startsWith("a");
         * })
         * .map(s -> {
         * System.out.println("map: " + s);
         * return s.toUpperCase();
         * })
         * .forEach(s -> System.out.println("forEach: " + s));
         */

    }

}