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
public class Sync {

    private static int count1 = 0;
    private int count2 = 0;

    public static synchronized void add1(int value) {
        /*
         * try {
         * Thread.sleep(100);
         * } catch (InterruptedException ex) {
         * 
         * }
         */
        count1 += value;
    }

    public static synchronized void subtract1(int value) {
        count1 -= value;
    }

    public synchronized void add2(int value) {
        /*
         * try {
         * Thread.sleep(100);
         * } catch (InterruptedException ex) {
         * 
         * }
         */
        count2 += value;
    }

    public synchronized void subtract2(int value) {
        count2 -= value;
    }

    public static int getCount1() {
        return count1;
    }

    public int getCount2() {
        return count2;
    }

}
