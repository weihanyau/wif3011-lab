package lab9.q2;

import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        IntStream.rangeClosed(1, 50).parallel().forEach((num) -> {
            if (isPrime(num)) {
                System.out.println("Thread " + Thread.currentThread().getId() + ": " + num);
            }
        });
    }

    public static boolean isPrime(int num) {
        if(num == 1) return false;
        if(num == 2) return true;
        return IntStream.range(2, num).boxed().reduce(true, (result, e) -> {
            if (num % e == 0) {
                return result && false;
            }
            return result && true;
        }, (a, b) -> a && b);
    }
}
