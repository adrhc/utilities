package ro.go.adrhc.util.concurrency.lock;

import com.rainerhahnekamp.sneakythrow.functional.SneakyRunnable;
import com.rainerhahnekamp.sneakythrow.functional.SneakySupplier;
import lombok.RequiredArgsConstructor;
import ro.go.adrhc.util.fn.ThrowableSupplier;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class SmartLock {
	private final Lock lock;

	public static SmartLock of() {
		return new SmartLock(new ReentrantLock());
	}

	public void synchronizeRun(Runnable runnable) {
		LockUtils.synchronizeRun(lock, runnable);
	}

	public <E extends Exception> void synchronizeUnsafeRun(SneakyRunnable<E> runnable) throws E {
		LockUtils.synchronizeUnsafeRun(lock, runnable);
	}

	public <T> T synchronizeGet(Supplier<T> supplier) {
		return LockUtils.synchronizeGet(lock, supplier);
	}

	public <T> T synchronizeGet(long waitMillis, Supplier<T> supplier)
		throws InterruptedException, LockWaitTimeoutException {
		return LockUtils.synchronizeGet(lock, waitMillis, supplier);
	}

	public <T, E extends Exception> T synchronizeUnsafeGet(SneakySupplier<T, E> supplier) throws E {
		return LockUtils.synchronizeUnsafeGet(lock, supplier);
	}

	public <T, E extends Exception> T
	synchronizeUnsafeGet(long waitMillis, SneakySupplier<T, E> supplier)
		throws E, InterruptedException, LockWaitTimeoutException {
		return LockUtils.synchronizeUnsafeGet(lock, waitMillis, supplier);
	}

	public <R, T extends Throwable> R
	synchronizeThrowableGet(ThrowableSupplier<R, T> supplier) throws T {
		return LockUtils.synchronizeThrowableGet(lock, supplier);
	}

	public <R, T extends Throwable> R
	synchronizeThrowableGet(long waitMillis, ThrowableSupplier<R, T> supplier)
		throws T, InterruptedException, LockWaitTimeoutException {
		return LockUtils.synchronizeThrowableGet(lock, waitMillis, supplier);
	}
}
