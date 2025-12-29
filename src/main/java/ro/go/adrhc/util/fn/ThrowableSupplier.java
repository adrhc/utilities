package ro.go.adrhc.util.fn;

@FunctionalInterface
public interface ThrowableSupplier<R, T extends Throwable> {
	R get() throws T;
}
