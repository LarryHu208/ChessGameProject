
import java.awt.Dimension;
import java.util.concurrent.locks.ReentrantLock;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple model, in the MVC sense of "model".
 */

public class MyModel {

    private Snapshot state;


    private List<MyObserver> observers = new ArrayList<MyObserver>();

    private final ReentrantLock lock = new ReentrantLock();

    public MyModel() {

        state = new Snapshot();
    }

    public void attach(MyObserver observer) {

        lock.lock();

        try {
            // This fixes the somewhat subtle bug:
            ArrayList<MyObserver> newList = new ArrayList<>(observers);
            newList.add(observer);
            observers = newList;
        } finally {
            lock.unlock();
        }
    }

    public void detach(MyObserver observer) {
        // We take out the lock to make this method thread-safe.
        // This doesn't fix our "somewhat subtle" bug, but it's a good
        // idea.
        lock.lock();

        try {
            // This fixes the somewhat subtle bug:
            ArrayList<MyObserver> newList = new ArrayList<>(observers);
            newList.remove(observer);
            observers = newList;
        } finally {
            lock.unlock();
        }
    }



    public Snapshot getState() {
        lock.lock();
        try {
            return state;
        } finally {
            lock.unlock();
        }
    }

    private void notifyObservers() {

        lock.lock();
            // Locking this guarantees we don't have a stale copy of
            // observers.
        List<MyObserver> observersLocal = observers;
        lock.unlock();

        for (MyObserver o : observersLocal) {
            o.notifyModelUpdated();
        }
    }

    private void setState(Snapshot state) {
        lock.lock();
        try {
            this.state = state;
        } finally {
            lock.unlock();
        }
        notifyObservers();
    }

    public void run() throws InterruptedException {

        for (;;) {
            lock.lock();
            try {
                if (observers.size() == 0) {
                    break;
                }
            } finally {
                lock.unlock();
            }

            Thread.sleep(30);

            notifyObservers();
        }
    }
}
