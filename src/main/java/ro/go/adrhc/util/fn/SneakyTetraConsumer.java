package ro.go.adrhc.util.fn;

@FunctionalInterface
public interface SneakyTetraConsumer<T, U, V, W, E extends Exception> {
	void accept(T t, U u, V v, W w) throws E;
}
