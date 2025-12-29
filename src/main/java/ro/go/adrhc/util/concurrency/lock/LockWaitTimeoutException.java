package ro.go.adrhc.util.concurrency.lock;

public class LockWaitTimeoutException extends Exception {
	public LockWaitTimeoutException(long waitMillis) {
		super("Failed to acquire lock within " + waitMillis + " ms!");
	}
}
