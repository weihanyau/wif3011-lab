package lab8.q3;

public class Main {
    public static void main(String[] args) {
        CalcPerfectSquare perfectSquare = (n) -> {
            int sqrt = (int) Math.sqrt(n);
            
            return sqrt * sqrt == n;
        };
        System.out.println(perfectSquare.isPerfectSquare(25));
        System.out.println(perfectSquare.isPerfectSquare(26));
    }
}

interface CalcPerfectSquare {
    boolean isPerfectSquare(int n);
}