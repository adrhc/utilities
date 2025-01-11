package ro.go.adrhc.util.fn;

@FunctionalInterface
public interface SneakyTriFunction<T, U, V, R, E extends Exception> {
	R apply(T t, U u, V v) throws E;
}
