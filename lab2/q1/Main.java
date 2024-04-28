package lab2.q1;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random r = new Random();
        int[] numbers = new int[1000000];

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = r.nextInt(50000) + 1;
        }

        System.out.println("The largest number is: " + LargestNumberSequential.findLargestNumber(numbers));
    }

    static class LargestNumberSequential {
        public static int findLargestNumber(int[] array) {
            int max = array[0];

            for (int i = 1; i < array.length; i++) {
                max = Math.max(max, array[i]);
            }

            return max;
        }
    }
}
