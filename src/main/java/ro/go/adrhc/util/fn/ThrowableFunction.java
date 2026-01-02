package ro.go.adrhc.util.fn;

@FunctionalInterface
public interface ThrowableFunction<I, R, T extends Throwable> {
	R apply(I input) throws T;
}
