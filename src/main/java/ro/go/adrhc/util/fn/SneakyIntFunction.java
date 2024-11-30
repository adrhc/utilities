package ro.go.adrhc.util.fn;

@FunctionalInterface
public interface SneakyIntFunction<R, E extends Exception> {
	R apply(Integer value) throws E;
}
