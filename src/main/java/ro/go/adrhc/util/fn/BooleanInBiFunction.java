package ro.go.adrhc.util.fn;

@FunctionalInterface
public interface BooleanInBiFunction<T, R> {
	R apply(boolean input, T t);
}
