package lab4;

import java.util.Random;

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
        private Object lock = new Object();
        private boolean valueChanged = false;

        public Node() {
        }

        public void setValue(E value) {
            synchronized (lock) {
                System.out.println("Setting value to " + value);
                this.value = value;
                valueChanged = true;
                lock.notifyAll();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void executeOnValue(E desiredValue, Runnable task) {
            synchronized (lock) {
                while (value == null || !valueChanged || !value.equals(desiredValue)) {
                    if (value != null && valueChanged) {
                        System.out.println("Checking value " + value + " against " + desiredValue);
                    }
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                valueChanged = false;
                task.run();
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
