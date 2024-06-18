package lab8.q4;

public class Main {
    public static void main(String[] args) {
        CheckPalindrome palindrome = (s) -> {
            String reverse = new StringBuilder(s).reverse().toString();
            return s.equals(reverse);
        };
        System.out.println(palindrome.isPalindrome("racecar"));
        System.out.println(palindrome.isPalindrome("hello"));
    }

    interface CheckPalindrome {
        boolean isPalindrome(String s);
    }
}
