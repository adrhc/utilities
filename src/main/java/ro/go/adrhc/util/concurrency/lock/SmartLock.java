package ro.go.adrhc.util.concurrency.lock;

import com.rainerhahnekamp.sneakythrow.functional.SneakyRunnable;
import com.rainerhahnekamp.sneakythrow.functional.SneakySupplier;
import ro.go.adrhc.util.fn.ThrowableSupplier;

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

	public <T> T synchronize(Supplier<T> supplier) {
		lock();
		try {
			return supplier.get();
		} finally {
			unlock();
		}
	}

	public <T> T synchronize(long waitMillis, Supplier<T> supplier)
		throws InterruptedException, LockWaitTimeoutException {
		if (tryLock() || tryLock(waitMillis, TimeUnit.MILLISECONDS)) {
			try {
				return supplier.get();
			} finally {
				unlock();
			}
		} else {
			throw new LockWaitTimeoutException(waitMillis);
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

	public <T> T synchronizeUnsafe(ThrowableSupplier<T> supplier) throws Throwable {
		lock();
		try {
			return supplier.get();
		} finally {
			unlock();
		}
	}

	public <T, E extends Exception> T
	synchronizeUnsafe(long waitMillis, SneakySupplier<T, E> supplier)
		throws E, InterruptedException, LockWaitTimeoutException {
		if (tryLock() || tryLock(waitMillis, TimeUnit.MILLISECONDS)) {
			try {
				return supplier.get();
			} finally {
				unlock();
			}
		} else {
			throw new LockWaitTimeoutException(waitMillis);
		}
	}
}
