package ro.go.adrhc.util.concurrency.lock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

	public void lockAllForWrite() {
		readWriteLocks.values().stream().map(ReadWriteLock::writeLock).forEach(Lock::lock);
	}

	public void unlockAllForWrite() {
		readWriteLocks.values().stream().map(ReadWriteLock::writeLock).forEach(Lock::unlock);
	}

	/**
	 * Wait "waitMillis" milliseconds.
	 *
	 * @return supplier's outcome or empty if the lock can't be obtained
	 */
	public <R, T extends Throwable> Optional<R> getFastWithReadLock(
		String lockName, long waitMillis, ThrowableSupplier<R, T> supplier) throws T {
		Lock lock = readWriteLocks.get(lockName).readLock();
		return LockUtils.getFastExclusively(lock, waitMillis, supplier);
	}

	/**
	 * Wait "waitMillis" milliseconds.
	 *
	 * @return supplier's outcome or empty if the lock can't be obtained
	 */
	public <R, T extends Throwable> Optional<R> getFastWithWriteLock(
		String lockName, long waitMillis, ThrowableSupplier<R, T> supplier) throws T {
		Lock lock = readWriteLocks.get(lockName).writeLock();
		return LockUtils.getFastExclusively(lock, waitMillis, supplier);
	}

	public <R, T extends Throwable> R syncThrowableGetForRead(
		String lockName, ThrowableSupplier<R, T> supplier) throws T {
		Lock lock = readWriteLocks.get(lockName).readLock();
		return LockUtils.synchronizeThrowableGet(lock, supplier);
	}

	public <R, T extends Throwable> R syncThrowableGetForRead(
		String lockName, long waitMillis, ThrowableSupplier<R, T> supplier)
		throws T, LockWaitTimeoutException, InterruptedException {
		Lock lock = readWriteLocks.get(lockName).readLock();
		return LockUtils.synchronizeThrowableGet(lock, waitMillis, supplier);
	}

	public <R, T extends Throwable> R syncThrowableGetForWrite(
		String lockName, ThrowableSupplier<R, T> supplier) throws T {
		Lock lock = readWriteLocks.get(lockName).writeLock();
		return LockUtils.synchronizeThrowableGet(lock, supplier);
	}

	public <R, T extends Throwable> R syncThrowableGetForWrite(
		String lockName, long waitMillis, ThrowableSupplier<R, T> supplier)
		throws T, LockWaitTimeoutException, InterruptedException {
		Lock lock = readWriteLocks.get(lockName).writeLock();
		return LockUtils.synchronizeThrowableGet(lock, waitMillis, supplier);
	}

	public Set<String> lockNames() {
		return readWriteLocks.keySet();
	}
}
