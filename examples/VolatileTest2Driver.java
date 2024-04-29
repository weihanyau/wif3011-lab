package examples;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Chiew Thiam Kian
 */
public class VolatileTest2Driver implements Runnable {

    private VolatileTest2 vt2;

    public VolatileTest2Driver(VolatileTest2 vt2) {
        this.vt2 = vt2;
    }

    @Override
    public void run() {
        print();
        set();
    }

    public void print() {
        System.out.println("Thread " + Thread.currentThread().getId() + ": print " + vt2.getVol());
    }

    public void set() {
        int x = new Random().nextInt(10);
        System.out.println("Thread " + Thread.currentThread().getId() + ": set " + x);
        vt2.setVol(x);
    }

    public static void main(String[] args) {
        VolatileTest2 vt = new VolatileTest2(0);
        Runnable r1 = new VolatileTest2Driver(vt);
        Runnable r2 = new VolatileTest2Driver(vt);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(r1);
        executor.execute(r2);
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        System.out.println("Thread " + Thread.currentThread().getId() + ": print " + vt.getVol());
    }
}
