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
public class SyncWorker implements Runnable {

    private Sync data1 = new Sync();

    public SyncWorker(Sync data1) {
        this.data1 = data1;
    }

    public void run() {
        Sync.add1(5);
        System.out.println(Thread.currentThread().getId() + ": Static data: count1 = " + Sync.getCount1());

        data1.add2(5);
        System.out.println(Thread.currentThread().getId() + ": Instace data: count 2 of data1 = " + data1.getCount2());

    }

    public static void main(String[] args) {
        Sync data1 = new Sync();
        Runnable r1 = new SyncWorker(data1);
        Runnable r2 = new SyncWorker(data1);
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.start();
        t2.start();
    }
}
