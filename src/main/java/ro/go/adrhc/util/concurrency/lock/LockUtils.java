package ro.go.adrhc.util.concurrency.lock;

import com.rainerhahnekamp.sneakythrow.functional.SneakyRunnable;
import com.rainerhahnekamp.sneakythrow.functional.SneakySupplier;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ro.go.adrhc.util.fn.ThrowableConsumer;
import ro.go.adrhc.util.fn.ThrowableFunction;
import ro.go.adrhc.util.fn.ThrowableSupplier;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@UtilityClass
@Slf4j
public class LockUtils {
	/**
	 * @return supplier's outcome or empty if the lock can't be obtained
	 */
	public static <R, T extends Throwable> Optional<R>
	getNowExclusively(Lock lock, ThrowableSupplier<R, T> supplier) throws T {
		if (lock.tryLock()) {
			return Optional.ofNullable(getThenUnlock(lock, supplier));
		} else {
			return Optional.empty();
		}
	}

	/**
	 * Wait "waitMillisForFast" milliseconds.
	 *
	 * @return supplier's outcome or empty if the lock can't be obtained
	 */
	public static <R, T extends Throwable> Optional<R>
	getFastExclusively(Lock lock, long waitMillis, ThrowableSupplier<R, T> supplier) throws T {
		try {
			if (lock.tryLock() || lock.tryLock(waitMillis, TimeUnit.MILLISECONDS)) {
				return Optional.ofNullable(getThenUnlock(lock, supplier));
			} else {
				return Optional.empty();
			}
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
			return Optional.empty();
		}
	}

	/**
	 * Wait "waitMillisForFast" milliseconds.
	 *
	 * @return supplier's outcome or empty if the lock can't be obtained
	 */
	public static <I, R, T extends Throwable> Optional<R>
	applyThrowableFastExclusively(Lock lock, long waitMillis, I input, ThrowableFunction<I, R, T> fn)
		throws T {
		try {
			if (lock.tryLock() || lock.tryLock(waitMillis, TimeUnit.MILLISECONDS)) {
				return Optional.ofNullable(applyThenUnlock(lock, input, fn));
			} else {
				return Optional.empty();
			}
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
			return Optional.empty();
		}
	}

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

	private static <R, T extends Throwable> R
	getThenUnlock(Lock lock, ThrowableSupplier<R, T> supplier) throws T {
		try {
			return supplier.get();
		} finally {
			lock.unlock();
		}
	}

	private static <I, R, T extends Throwable> R
	applyThenUnlock(Lock lock, I input, ThrowableFunction<I, R, T> fn) throws T {
		try {
			return fn.apply(input);
		} finally {
			lock.unlock();
		}
	}
}
