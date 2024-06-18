/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examples;

/**
 *
 * @author Chiew Thiam Kian
 */
public class LambdaBasic {

    public static void main(String args[]) {
        LambdaBasic lamb = new LambdaBasic();

        //with type declaration
        MathOperation addition = (int a, int b) -> a + b;

        //without type declaration
        MathOperation subtraction = (a, b) -> a - b;

        //with return statement along with curly braces
        MathOperation multiplication = (int a, int b) -> {
            return a * b;
        };

        //without return statement and without curly braces
        MathOperation division = (a, b) -> a / b;

        System.out.println("10 + 5 = " + lamb.operate(10, 5, addition));
        System.out.println("10 - 5 = " + lamb.operate(10, 5, subtraction));
        System.out.println("10 x 5 = " + lamb.operate(10, 5, multiplication));
        System.out.println("10 / 5 = " + lamb.operate(10, 5, division));

        //without parenthesis
        GreetingService greetService1 = message
                -> System.out.println("Hello " + message);

        //with parenthesis
        GreetingService greetService2 = (message)
                -> System.out.println("Hello " + message);

        greetService1.sayMessage("Mahesh");
        greetService2.sayMessage("Suresh");
    }

    interface MathOperation {

        int operation(int a, int b);
    }

    interface GreetingService {

        void sayMessage(String message);
    }

    private int operate(int a, int b, MathOperation mathOperation) {
        return mathOperation.operation(a, b);
    }
}
