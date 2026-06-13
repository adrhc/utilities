package ro.go.adrhc.util.concurrency.lock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.go.adrhc.util.fn.ThrowableSupplier;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@RequiredArgsConstructor
@Slf4j
public class SmartLockMap {
	private final Map<String, SmartLock> locks;

	public static SmartLockMap of(Stream<String> lockNames) {
		Map<String, SmartLock> locks = lockNames.collect(toMap(identity(), name -> SmartLock.of()));
		return new SmartLockMap(locks);
	}

	public <R, T extends Throwable> R getSafelyExclusively(
		String lockName, ThrowableSupplier<R, T> supplier) throws T {
		return locks.get(lockName).getSafelyExclusively(supplier);
	}

	public <R, T extends Throwable> R getOnTimeSafelyExclusively(
		String lockName, long waitMillis, ThrowableSupplier<R, T> supplier)
		throws T, LockWaitTimeoutException, InterruptedException {
		return locks.get(lockName).getOnTimeSafelyExclusively(waitMillis, supplier);
	}

	/**
	 * Wait "this.waitMillis" milliseconds.
	 *
	 * @return supplier's outcome or empty if the lock can't be obtained
	 */
	public <R, T extends Throwable> Optional<R> getOptionallyOnTimeSafelyExclusively(
		String lockName, long waitMillis, ThrowableSupplier<R, T> supplier) throws T {
		return locks.get(lockName).getOptionallyOnTimeSafelyExclusively(waitMillis, supplier);
	}

	public void lockAll() {
		locks.values().forEach(SmartLock::lock);
	}

	public void unlockAll() {
		locks.values().forEach(SmartLock::unlock);
	}

	public Set<String> lockNames() {
		return locks.keySet();
	}
}
