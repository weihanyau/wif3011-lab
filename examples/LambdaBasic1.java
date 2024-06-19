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
public class LambdaBasic1 {

    interface NumericTest {

        //single parameter
        boolean computeTest(int n);
    }

    interface Addable {

        //multiple parameters
        int add(int a, int b);
    }

    interface MyString {

        String myStringFunction(String str);
    }

    //Generic functional interface
    interface MyGeneric<T> {

        T compute(T t);
    }

    //passing a lambda expression as an argument
    public static String reverseStr(MyString reverse, String str) {
        return reverse.myStringFunction(str);
    }

    public static void main(String args[]) {
        NumericTest isEven = (n) -> (n % 2) == 0;
        NumericTest isNegative = (n) -> (n < 0);

        // Output: false
        System.out.println(isEven.computeTest(5));
        // Output: true
        System.out.println(isNegative.computeTest(-5));

        /**
         * ********************************************
         */
        // Multiple parameters in lambda expression  
        Addable ad1 = (a, b) -> (a + b);
        System.out.println(ad1.add(10, 20));
        // Multiple parameters with data type in lambda expression  
        Addable ad2 = (int a, int b) -> (a + b);
        System.out.println(ad2.add(100, 200));
        // Lambda expression with return keyword.    
        Addable ad3 = (int a, int b) -> {
            return (a + b);
        };
        System.out.println(ad3.add(100, 200));

        /**
         * ********************************************
         */
        // Block lambda to reverse string
        MyString reverseStr = (str) -> {
            String result = "";
            for (int i = str.length() - 1; i >= 0; i--) {
                result += str.charAt(i);
            }
            return result;
        };
        // Output: omeD adbmaL
        System.out.println(reverseStr.myStringFunction("Lambda Demo"));

        /**
         * ********************************************
         */
        // String version of MyGenericInteface
        MyGeneric<String> reverse = (str) -> {
            String result = "";
            for (int i = str.length() - 1; i >= 0; i--) {
                result += str.charAt(i);
            }
            return result;
        };

        // Integer version of MyGeneric
        MyGeneric<Integer> factorial = (Integer i) -> {
            int result = 1;
            int n = 5;
            for (i = 1; i <= n; i++) {
                result = i * result;
            }
            return result;
        };

        // Output: omeD adbmaL
        System.out.println(reverse.compute("Lambda Demo"));
        // Output: 120
        System.out.println(factorial.compute(5));

        /**
         * ********************************************
         */
        MyString reverse1 = (str) -> {
		String result = "";		
		for(int i = str.length()-1; i >= 0; i--)
			result += str.charAt(i);		
		return result;
	};

        // Output: omeD adbmaL
        System.out.println(reverseStr(reverse1, "Lambda Demo"));
    }

}
