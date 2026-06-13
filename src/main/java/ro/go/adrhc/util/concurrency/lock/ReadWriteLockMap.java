package ro.go.adrhc.util.concurrency.lock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.go.adrhc.util.fn.ThrowableRunnable;
import ro.go.adrhc.util.fn.ThrowableSupplier;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

/**
 * A ReadWriteLock maintains a pair of associated locks, one for read-only operations and one for writing.
 * The read lock may be held simultaneously by multiple reader threads, so long as there are no writers.
 * The write lock is exclusive.
 */
@RequiredArgsConstructor
@Slf4j
public class ReadWriteLockMap {
	private final Map<String, ReadWriteLock> readWriteLocks;

	public static ReadWriteLockMap of(Stream<String> lockNames) {
		Map<String, ReadWriteLock> locks = lockNames.collect(
			toMap(identity(), name -> new ReentrantReadWriteLock()));
		return new ReadWriteLockMap(locks);
	}

	public <R, T extends Throwable> R getWithExclusiveRead(ThrowableSupplier<R, T> supplier) throws T {
		lockAllForRead();
		try {
			return supplier.get();
		} finally {
			unlockAllForRead();
		}
	}

	public <R, T extends Throwable> R getWithExclusiveWrite(ThrowableSupplier<R, T> supplier) throws T {
		lockAllForWrite();
		try {
			return supplier.get();
		} finally {
			unlockAllForWrite();
		}
	}

	public void lockAllForRead() {
		readWriteLocks.values().stream().map(ReadWriteLock::readLock).forEach(Lock::lock);
	}

	public void unlockAllForRead() {
		readWriteLocks.values().stream().map(ReadWriteLock::readLock).forEach(Lock::unlock);
	}

	public void lockAllForWrite() {
		readWriteLocks.values().stream().map(ReadWriteLock::writeLock).forEach(Lock::lock);
	}

	public void unlockAllForWrite() {
		readWriteLocks.values().stream().map(ReadWriteLock::writeLock).forEach(Lock::unlock);
	}

	public <T extends Throwable> void runWithExclusiveRead(
		String lockName, ThrowableRunnable<T> runnable) throws T {
		Lock lock = readWriteLocks.get(lockName).readLock();
		LockUtils.runExclusively(lock, runnable);
	}

	public <T extends Throwable> void runWithExclusiveWrite(
		String lockName, ThrowableRunnable<T> runnable) throws T {
		Lock lock = readWriteLocks.get(lockName).writeLock();
		LockUtils.runExclusively(lock, runnable);
	}

	/**
	 * Wait "waitMillis" milliseconds.
	 *
	 * @return supplier's outcome or empty if the lock can't be obtained
	 */
	public <R, T extends Throwable> Optional<R> getOptionallyOnTimeWithExclusiveRead(
		String lockName, long waitMillis, ThrowableSupplier<R, T> supplier) throws T {
		Lock lock = readWriteLocks.get(lockName).readLock();
		return LockUtils.getOptionallyOnTimeExclusively(lock, waitMillis, supplier);
	}

	/**
	 * Wait "waitMillis" milliseconds.
	 *
	 * @return supplier's outcome or empty if the lock can't be obtained
	 */
	public <R, T extends Throwable> Optional<R> getOptionallyOnTimeWithExclusiveWrite(
		String lockName, long waitMillis, ThrowableSupplier<R, T> supplier) throws T {
		Lock lock = readWriteLocks.get(lockName).writeLock();
		return LockUtils.getOptionallyOnTimeExclusively(lock, waitMillis, supplier);
	}

	public <R, T extends Throwable> R getWithExclusiveRead(
		String lockName, ThrowableSupplier<R, T> supplier) throws T {
		Lock lock = readWriteLocks.get(lockName).readLock();
		return LockUtils.getExclusively(lock, supplier);
	}

	public <R, T extends Throwable> R getOnTimeWithExclusiveRead(
		String lockName, long waitMillis, ThrowableSupplier<R, T> supplier)
		throws T, LockWaitTimeoutException, InterruptedException {
		Lock lock = readWriteLocks.get(lockName).readLock();
		return LockUtils.getOnTimeExclusively(lock, waitMillis, supplier);
	}

	public <R, T extends Throwable> R getWithExclusiveWrite(
		String lockName, ThrowableSupplier<R, T> supplier) throws T {
		Lock lock = readWriteLocks.get(lockName).writeLock();
		return LockUtils.getExclusively(lock, supplier);
	}

	public <R, T extends Throwable> R getOnTimeWithExclusiveWrite(
		String lockName, long waitMillis, ThrowableSupplier<R, T> supplier)
		throws T, LockWaitTimeoutException, InterruptedException {
		Lock lock = readWriteLocks.get(lockName).writeLock();
		return LockUtils.getOnTimeExclusively(lock, waitMillis, supplier);
	}

	public Set<String> lockNames() {
		return readWriteLocks.keySet();
	}
}
