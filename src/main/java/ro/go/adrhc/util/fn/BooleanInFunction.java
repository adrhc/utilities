package ro.go.adrhc.util.fn;

@FunctionalInterface
public interface BooleanInFunction<R> {
	R apply(boolean input);
}
