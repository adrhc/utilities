package ro.go.adrhc.util.concurrency.lock;

import com.rainerhahnekamp.sneakythrow.functional.SneakyRunnable;
import com.rainerhahnekamp.sneakythrow.functional.SneakySupplier;

import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

public class SmartLock extends ReentrantLock {
    public <T> T synchronize(Supplier<T> supplier) {
        lock();
        try {
            return supplier.get();
        } finally {
            unlock();
        }
    }

    public <T, E extends Exception> T synchronize(SneakySupplier<T, E> supplier) throws E {
        lock();
        try {
            return supplier.get();
        } finally {
            unlock();
        }
    }

    public void synchronize(Runnable runnable) {
        lock();
        try {
            runnable.run();
        } finally {
            unlock();
        }
    }

    public <E extends Exception> void synchronize(SneakyRunnable<E> runnable) throws E {
        lock();
        try {
            runnable.run();
        } finally {
            unlock();
        }
    }
}
