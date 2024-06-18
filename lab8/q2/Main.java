package lab8.q2;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            list.add(i);
        }
        
        // All elements
        evaluate(list, (n) -> {
            return true;
        });
        System.out.println();

        // Even elements
        evaluate(list, (n) -> {
            return n % 2 == 0;
        });
        System.out.println();

        // Odd elements
        evaluate(list, (n) -> {
            return n % 2 != 0;
        });
        System.out.println();

        // Elements greater than 5
        evaluate(list, (n) -> {
            return n > 5;
        });
    }

    public static void evaluate(List<Integer> list, Predicate predicate) {
        for (Integer n : list) {
            if (predicate.test(n)) {
                System.out.println(n);
            }
        }
    }
}

interface Predicate {
    boolean test(int n); 
}
