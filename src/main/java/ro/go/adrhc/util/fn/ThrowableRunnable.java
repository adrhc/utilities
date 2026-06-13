package ro.go.adrhc.util.fn;

@FunctionalInterface
public interface ThrowableRunnable<T extends Throwable> {
	void run() throws T;
}
