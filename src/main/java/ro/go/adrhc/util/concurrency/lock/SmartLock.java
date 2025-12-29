package ro.go.adrhc.util.concurrency.lock;

import com.rainerhahnekamp.sneakythrow.functional.SneakyRunnable;
import com.rainerhahnekamp.sneakythrow.functional.SneakySupplier;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

public class SmartLock extends ReentrantLock {
	public void synchronize(Runnable runnable) {
		lock();
		try {
			runnable.run();
		} finally {
			unlock();
		}
	}

	public <T> T synchronize(int waitMillis, Supplier<T> supplier)
		throws InterruptedException, LockWaitTimeoutException {
		if (tryLock() || tryLock(waitMillis, TimeUnit.MILLISECONDS)) {
			try {
				return supplier.get();
			} finally {
				unlock();
			}
		} else {
			throw new LockWaitTimeoutException();
		}
	}

	public <T> T synchronize(Supplier<T> supplier) {
		lock();
		try {
			return supplier.get();
		} finally {
			unlock();
		}
	}

	public <E extends Exception> void synchronizeUnsafe(SneakyRunnable<E> runnable) throws E {
		lock();
		try {
			runnable.run();
		} finally {
			unlock();
		}
	}

	public <T, E extends Exception> T synchronizeUnsafe(SneakySupplier<T, E> supplier) throws E {
		lock();
		try {
			return supplier.get();
		} finally {
			unlock();
		}
	}
}
