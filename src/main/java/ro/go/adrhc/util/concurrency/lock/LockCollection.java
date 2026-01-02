package ro.go.adrhc.util.concurrency.lock;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.locks.Lock;

@RequiredArgsConstructor
public class LockCollection {
	private final List<Lock> locks;

	public static LockCollection of(Lock... addElementLock) {
		return new LockCollection(Arrays.asList(addElementLock));
	}

	public void synchronizeRun(Runnable action) {
		lockAll();
		try {
			action.run();
		} finally {
			unlockAll();
		}
	}

	public void lockAll() {
		locks.forEach(Lock::lock);
	}

	public void unlockAll() {
		ListIterator<Lock> listIterator = locks.listIterator(locks.size());
		while (listIterator.hasPrevious()) {
			listIterator.previous().unlock();
		}
	}
}
