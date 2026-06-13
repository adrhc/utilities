package ro.go.adrhc.util.concurrency.lock;

import lombok.RequiredArgsConstructor;
import ro.go.adrhc.util.fn.ThrowableRunnable;
import ro.go.adrhc.util.fn.ThrowableSupplier;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class SmartLock {
	private final Lock lock;

	public static SmartLock of() {
		return new SmartLock(new ReentrantLock());
	}

	public Condition newCondition() {
		return lock.newCondition();
	}

	public boolean tryLock() {
		return lock.tryLock();
	}

	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return lock.tryLock(time, unit);
	}

	public void lock() {
		lock.lock();
	}

	public void unlock() {
		lock.unlock();
	}

	public <R, T extends Throwable> Optional<R>
	getOptionallyNowSafelyExclusively(ThrowableSupplier<R, T> supplier) throws T {
		return LockUtils.getOptionallyNowSafelyExclusively(lock, supplier);
	}

	public <R, T extends Throwable> Optional<R>
	getOptionallyOnTimeSafelyExclusively(long waitMillis, ThrowableSupplier<R, T> supplier) throws T {
		return LockUtils.getOptionallyOnTimeSafelyExclusively(lock, waitMillis, supplier);
	}

	public void runExclusively(Runnable runnable) {
		LockUtils.runExclusively(lock, runnable);
	}

	public <T extends Throwable> void runSafelyExclusively(ThrowableRunnable<T> runnable) throws T {
		LockUtils.runSafelyExclusively(lock, runnable);
	}

	public <O, R> R applyExclusively(O o, Function<O, R> fn) {
		return LockUtils.applyExclusively(lock, o, fn);
	}

	public <R> R getExclusively(Supplier<R> supplier) {
		return LockUtils.getExclusively(lock, supplier);
	}

	public <R> R getOnTimeExclusively(long waitMillis, Supplier<R> supplier)
		throws InterruptedException, LockWaitTimeoutException {
		return LockUtils.getOnTimeExclusively(lock, waitMillis, supplier);
	}

	public <R, T extends Throwable> R
	getOnTimeSafelyExclusively(long waitMillis, ThrowableSupplier<R, T> supplier)
		throws T, InterruptedException, LockWaitTimeoutException {
		return LockUtils.getOnTimeSafelyExclusively(lock, waitMillis, supplier);
	}

	public <R, T extends Throwable> R getSafelyExclusively(ThrowableSupplier<R, T> supplier) throws T {
		return LockUtils.getSafelyExclusively(lock, supplier);
	}
}
