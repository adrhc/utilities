package ro.go.adrhc.util.concurrency.lock;

import com.rainerhahnekamp.sneakythrow.functional.SneakyRunnable;
import com.rainerhahnekamp.sneakythrow.functional.SneakySupplier;
import lombok.experimental.UtilityClass;
import ro.go.adrhc.util.fn.ThrowableConsumer;
import ro.go.adrhc.util.fn.ThrowableSupplier;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@UtilityClass
public class LockUtils {
	public static void synchronizeRun(Lock lock, Runnable runnable) {
		lock.lock();
		try {
			runnable.run();
		} finally {
			lock.unlock();
		}
	}

	public static <O, R> R synchronizeApply(Lock lock, O o, Function<O, R> fn) {
		lock.lock();
		try {
			return fn.apply(o);
		} finally {
			lock.unlock();
		}
	}

	/**
	 * @param fn is executed with the provided lock
	 */
	public static <O, R> R synchronizeApply(
		Lock lock, long waitMillis, O o, Function<O, R> fn)
		throws InterruptedException, LockWaitTimeoutException {
		if (lock.tryLock() || lock.tryLock(waitMillis, TimeUnit.MILLISECONDS)) {
			try {
				return fn.apply(o);
			} finally {
				lock.unlock();
			}
		}
		throw new LockWaitTimeoutException(waitMillis);
	}

	/**
	 * @param consumer is run with the provided lock
	 */
	public static <O> void synchronizeAccept(Lock lock, O o, Consumer<O> consumer) {
		lock.lock();
		try {
			consumer.accept(o);
		} finally {
			lock.unlock();
		}
	}

	public static <E extends Exception> void
	synchronizeUnsafeRun(Lock lock, SneakyRunnable<E> runnable) throws E {
		lock.lock();
		try {
			runnable.run();
		} finally {
			lock.unlock();
		}
	}

	public static <T> T synchronizeGet(Lock lock, Supplier<T> supplier) {
		lock.lock();
		try {
			return supplier.get();
		} finally {
			lock.unlock();
		}
	}

	public static <T> T synchronizeGet(Lock lock, long waitMillis, Supplier<T> supplier)
		throws InterruptedException, LockWaitTimeoutException {
		if (lock.tryLock() || lock.tryLock(waitMillis, TimeUnit.MILLISECONDS)) {
			try {
				return supplier.get();
			} finally {
				lock.unlock();
			}
		} else {
			throw new LockWaitTimeoutException(waitMillis);
		}
	}

	public static <R, E extends Exception> R
	synchronizeUnsafeGet(Lock lock, SneakySupplier<R, E> supplier) throws E {
		lock.lock();
		try {
			return supplier.get();
		} finally {
			lock.unlock();
		}
	}

	public static <R, E extends Exception> R synchronizeUnsafeGet(
		Lock lock, long waitMillis, SneakySupplier<R, E> supplier)
		throws E, InterruptedException, LockWaitTimeoutException {
		if (lock.tryLock() || lock.tryLock(waitMillis, TimeUnit.MILLISECONDS)) {
			try {
				return supplier.get();
			} finally {
				lock.unlock();
			}
		} else {
			throw new LockWaitTimeoutException(waitMillis);
		}
	}

	public static <R, T extends Throwable> R
	synchronizeThrowableGet(Lock lock, ThrowableSupplier<R, T> supplier) throws T {
		lock.lock();
		try {
			return supplier.get();
		} finally {
			lock.unlock();
		}
	}

	public static <R, T extends Throwable> R synchronizeThrowableGet(
		Lock lock, long waitMillis, ThrowableSupplier<R, T> supplier)
		throws T, InterruptedException, LockWaitTimeoutException {
		if (lock.tryLock() || lock.tryLock(waitMillis, TimeUnit.MILLISECONDS)) {
			try {
				return supplier.get();
			} finally {
				lock.unlock();
			}
		} else {
			throw new LockWaitTimeoutException(waitMillis);
		}
	}

	public static <O, T extends Throwable> void synchronizeAccept(
		Lock lock, long waitMillis, O o, ThrowableConsumer<O, T> consumer)
		throws T, InterruptedException, LockWaitTimeoutException {
		if (lock.tryLock() || lock.tryLock(waitMillis, TimeUnit.MILLISECONDS)) {
			try {
				consumer.accept(o);
			} finally {
				lock.unlock();
			}
		} else {
			throw new LockWaitTimeoutException(waitMillis);
		}
	}
}
