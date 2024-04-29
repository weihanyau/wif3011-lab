package lab4;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) {
        Node<Integer> node = new Node<Integer>();

        Thread write = new Thread(new Write(node));
        Thread operate1 = new Thread(new Operate(node, 1));

        write.start();
        operate1.start();
    }

    static class Node<E> {
        private E value = null;
        private Lock lock = new ReentrantLock();
        private Condition valueChanged = lock.newCondition();

        public void setValue(E value) {
            lock.lock();
            System.out.println("Setting value to " + value);
            this.value = value;
            valueChanged.signalAll();
            lock.unlock();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void executeOnValue(E desiredValue, Runnable task) {
            lock.lock();
            try {
                do {
                    valueChanged.await();
                    System.out.println("Checking value " + value + " against " + desiredValue);
                } while (desiredValue != value);
                task.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

    }

    static class Write implements Runnable {
        private Node<Integer> node;

        public Write(Node<Integer> node) {
            this.node = node;
        }

        @Override
        public void run() {
            Random r = new Random();
            while (true) {
                node.setValue(r.nextInt(5));
            }
        }
    }

    static class Operate implements Runnable {
        private Node<Integer> node;
        private int target;
        private Runnable dummy = new Dummy();

        public Operate(Node<Integer> node, int target) {
            this.node = node;
            this.target = target;
        }

        @Override
        public void run() {
            int foundCount = 0;
            while (true) {
                node.executeOnValue(target, dummy);
                foundCount++;
                if (foundCount == 2) {
                    System.exit(0);
                }
            }
        }
    }

    static class Dummy implements Runnable {
        @Override
        public void run() {
            System.out.println("The desired value is found!");
        }
    }
}
