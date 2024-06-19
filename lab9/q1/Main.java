package lab9.q1;

import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Employee[] employees = new Employee[]{
            new Employee("Poseidon", 'M', 23),
            new Employee("Hera", 'F', 18),
            new Employee("Apollo", 'M', 20),
            new Employee("Athena", 'F', 35),
            new Employee("Demeter", 'F', 50),
        };

        // First solution
        Stream.of(employees).filter((employee) -> {
            return employee.age >= 21 && employee.gender == 'F';
        }).forEach((employee) -> {
            System.out.println(employee.name);
        });
        System.out.println();

        // Second solution
        Stream.of(employees).forEach(
            (employee) -> {
                if (employee.age >= 21 && employee.gender == 'F'){
                    System.out.println(employee.name);
                }
            }
        );
    }

    static class Employee {
        private String name;
        private char gender;
        private int age;

        public Employee(String name, char gender, int age) {
            this.name = name;
            this.gender = gender;
            this.age = age;
        }
    }
}
