package ro.go.adrhc.util.fn;

@FunctionalInterface
public interface ToBooleanFunction<T> {
	boolean apply(T t);
}
