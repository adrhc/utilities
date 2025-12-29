package ro.go.adrhc.util.fn;

@FunctionalInterface
public interface ThrowableSupplier<T> {
	T get() throws Throwable;
}
