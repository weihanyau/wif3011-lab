/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examples;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Chiew Thiam Kian
 */
class Person {

    String name;
    int age;

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return name;
    }

    public static void main(String[] args) {
        List<Person> persons = Arrays.asList(
                new Person("Max", 18),
                new Person("Peter", 23),
                new Person("Pamela", 23),
                new Person("David", 12));

        List<Person> filtered = persons
                .stream()
                .filter(p -> p.name.startsWith("P"))
                .collect(Collectors.toList());
        System.out.println(filtered); // [Peter, Pamela]

        Map<Integer, List<Person>> personsByAge = persons
                .stream()
                .collect(Collectors.groupingBy(p -> p.age));

        personsByAge
                .forEach((age, p) -> System.out.format("age %s: %s\n", age, p));

        // age 18: [Max]
        // age 23: [Peter, Pamela]
        // age 12: [David]
        Double averageAge = persons
                .stream()
                .collect(Collectors.averagingInt(p -> p.age));

        System.out.println(averageAge); // 19.0

        IntSummaryStatistics ageSummary = persons
                .stream()
                .collect(Collectors.summarizingInt(p -> p.age));

        System.out.println(ageSummary);
        // IntSummaryStatistics{count=4, sum=76, min=12, average=19.000000, max=23}

        String phrase = persons
                .stream()
                .filter(p -> p.age >= 18)
                .map(p -> p.name)
                .collect(Collectors.joining(" and ", "In Malaysia ", " are eligible to vote."));

        System.out.println(phrase);
        // In Malaysia Max and Peter and Pamela are eligible to vote.

        Map<Integer, String> map = persons
                .stream()
                .collect(Collectors.toMap(
                        p -> p.age,
                        p -> p.name,
                        (name1, name2) -> name1 + ";" + name2));

        System.out.println(map);

        Arrays.asList("a1", "a2", "b1", "c2", "c1")
                .parallelStream()
                .filter(s -> {
                    System.out.format("filter: %s [%s]\n",
                            s, Thread.currentThread().getName());
                    return true;
                })
                .map(s -> {
                    System.out.format("map: %s [%s]\n",
                            s, Thread.currentThread().getName());
                    return s.toUpperCase();
                })
                .forEach(s -> System.out.format("forEach: %s [%s]\n",
                        s, Thread.currentThread().getName()));
        System.out.println(" ");
        Arrays.asList("a1", "a2", "b1", "c2", "c1")
                .parallelStream()
                .filter(s -> {
                    System.out.format("filter: %s [%s]\n",
                            s, Thread.currentThread().getName());
                    return true;
                })
                .map(s -> {
                    System.out.format("map: %s [%s]\n",
                            s, Thread.currentThread().getName());
                    return s.toUpperCase();
                })
                .sorted((s1, s2) -> {
                    System.out.format("sort: %s <> %s [%s]\n",
                            s1, s2, Thread.currentThread().getName());
                    return s1.compareTo(s2);
                })
                .forEach(s -> System.out.format("forEach: %s [%s]\n",
                        s, Thread.currentThread().getName()));

    }
}