package ro.go.adrhc.util.fn;

@FunctionalInterface
public interface SneakyBooleanSupplier<E extends Exception> {
	boolean get() throws E;
}
