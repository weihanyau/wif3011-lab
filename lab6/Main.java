package lab6;

import java.util.Random;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Main
 */
public class Main {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        StackAccess<Integer> stackAccess = new StackAccess<Integer>();
        executorService.execute(new WriteStack(stackAccess));
        executorService.execute(new ReadStack(stackAccess));
        executorService.execute(new PeekStack(stackAccess));
        executorService.shutdown();
    }

    static class StackAccess<E> {
        private Stack<E> stack;
        private Lock lock = new ReentrantLock();
        private Condition stackChanged = lock.newCondition();

        public StackAccess() {
            stack = new Stack<E>();
        }

        public void push(E element) {
            lock.lock();
            while (stack.size() == 3) {
                try {
                    boolean awaited = stackChanged.await(1, TimeUnit.SECONDS);
                    if (!awaited) {
                        lock.unlock();
                        return;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            stack.push(element);
            stackChanged.signalAll();
            lock.unlock();
        }

        public E pop() {
            lock.lock();
            while (stack.size() == 0) {
                try {
                    boolean awaited = stackChanged.await(1, TimeUnit.SECONDS);
                    if(!awaited){
                        lock.unlock();
                        return null;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            E result = stack.pop();
            stackChanged.signalAll();
            lock.unlock();
            return result;
        }

        public E peek() {
            lock.lock();
            while (stack.size() == 0) {
                try {
                    boolean awaited = stackChanged.await(1, TimeUnit.SECONDS);
                    if (!awaited) {
                        lock.unlock();
                        return null;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            E result = stack.peek();
            stackChanged.signalAll();
            lock.unlock();
            return result;
        }
    }

    static class ReadStack implements Runnable {
        private StackAccess<Integer> stackAccess;

        public ReadStack(StackAccess<Integer> stackAccess) {
            this.stackAccess = stackAccess;
        }

        @Override
        public void run() {
            for (int index = 0; index < 4; index++) {
                System.out.println("Popped " + stackAccess.pop());
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    static class WriteStack implements Runnable {
        private StackAccess<Integer> stackAccess;

        public WriteStack(StackAccess<Integer> stackAccess) {
            this.stackAccess = stackAccess;
        }

        @Override
        public void run() {
            Random r = new Random();
            for (int index = 0; index < 4; index++) {
                int value = r.nextInt(100);
                stackAccess.push(value);
                System.out.println("Pushed " + value);
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    static class PeekStack implements Runnable {
        private StackAccess<Integer> stackAccess;

        public PeekStack(StackAccess<Integer> stackAccess) {
            this.stackAccess = stackAccess;
        }

        @Override
        public void run() {
            for (int index = 0; index < 4; index++) {
                System.out.println("Peeked " + stackAccess.peek());
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}