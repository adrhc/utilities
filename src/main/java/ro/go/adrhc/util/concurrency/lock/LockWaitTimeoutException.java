package ro.go.adrhc.util.concurrency.lock;

public class LockWaitTimeoutException extends RuntimeException {
	public LockWaitTimeoutException(long waitMillis) {
		super("Failed to acquire lock within " + waitMillis + " ms!");
	}
}
