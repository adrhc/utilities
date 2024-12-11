package ro.go.adrhc.util.fn;

@FunctionalInterface
public interface SneakyTriConsumer<T, U, V, E extends Exception> {
	void accept(T t, U u, V v) throws E;
}
