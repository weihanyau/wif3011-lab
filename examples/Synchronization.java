package examples;

class Update {

    static int sum = 0;

    void updateSum(int i) {
        Thread t = Thread.currentThread();
        sum += i;
        System.out.println(t.getName() + " : " + sum);
    }
}

class S extends Thread {

    Update u = new Update();

    public void run() {
        for (int i = 0; i < 10; i++) {
            u.updateSum(10);
        }
    }
}

public class Synchronization {

    public static void main(String[] args) {
        S s = new S();
        Thread t1 = new Thread(s);
        Thread t2 = new Thread(s);
        t1.setName("Thread 1");
        t2.setName("Thread 2");
        t1.start();
        t2.start();
    }

}