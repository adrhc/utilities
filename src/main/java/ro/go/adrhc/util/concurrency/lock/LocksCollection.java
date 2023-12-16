package ro.go.adrhc.util.concurrency.lock;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.locks.Lock;

@RequiredArgsConstructor
public class LocksCollection {
    private final List<Lock> locks;

    public static LocksCollection of(Lock... addElementLock) {
        return new LocksCollection(Arrays.asList(addElementLock));
    }

    public void execute(Runnable action) {
        lock();
        try {
            action.run();
        } finally {
            unlock();
        }
    }

    public void lock() {
        locks.forEach(Lock::lock);
    }

    public void unlock() {
        ListIterator<Lock> listIterator = locks.listIterator(locks.size());
        while (listIterator.hasPrevious()) {
            listIterator.previous().unlock();
        }
    }
}
