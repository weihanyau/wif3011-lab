package lab3.q1;

public class Main {
    public static void main(String[] args) {
        Room room = new Room();

        // Create and start cleaner threads
        Cleaner cleaner1 = new Cleaner(room);
        cleaner1.start();
        Cleaner cleaner2 = new Cleaner(room);
        cleaner2.start();

        // Create and start guest threads
        for (int i = 0; i < 4; i++) {
            Guest guest = new Guest(room);
            guest.start();
        }
        // Create and start cleaner threads
        Cleaner cleaner3 = new Cleaner(room);
        cleaner3.start();

        // Create and start guest threads
        for (int i = 0; i < 4; i++) {
            Guest guest = new Guest(room);
            guest.start();
        }
    }
}

class Room {
    private int numberOfGuests = 0;
    private boolean isCleanerInside = false;

    public synchronized void enterCleaner() throws InterruptedException {
        while (numberOfGuests > 0 || isCleanerInside) {
            System.out.println("Cleaner is waiting outside the room.");
            wait(); // Wait until no guests are in the room
        }
        isCleanerInside = true;
        System.out.println("Cleaner entered the room.");
    }

    public synchronized void exitCleaner() {
        isCleanerInside = false;
        notifyAll(); // Notify waiting guests that the cleaner has left
        System.out.println("Cleaner exited the room.");
    }

    public synchronized void enterGuest() throws InterruptedException {
        while (isCleanerInside || numberOfGuests >= 6) {
            System.out.println("Guest is waiting outside the room.");
            wait(); // Wait until the cleaner leaves or there are fewer than 6 guests
        }
        numberOfGuests++;
        System.out.println("Guest entered the room. Current number of guests: " + numberOfGuests);
    }

    public synchronized void exitGuest() {
        numberOfGuests--;
        notifyAll(); // Notify waiting cleaners and guests that a guest has left
        System.out.println("Guest exited the room. Current number of guests: " + numberOfGuests);
    }
}

class Cleaner extends Thread {
    private Room room;

    public Cleaner(Room room) {
        this.room = room;
    }

    public void run() {
        try {
            room.enterCleaner();
            // Simulate cleaning process
            sleep(2000);
            room.exitCleaner();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Guest extends Thread {
    private Room room;

    public Guest(Room room) {
        this.room = room;
    }

    public void run() {
        try {
            room.enterGuest();
            // Simulate guest staying in the room
            sleep(1000);
            room.exitGuest();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
