package ro.go.adrhc.util.fn;

@FunctionalInterface
public interface ThrowableBiFunction<P1, P2, R, T extends Throwable> {
	R apply(P1 p1, P2 p2) throws T;
}
