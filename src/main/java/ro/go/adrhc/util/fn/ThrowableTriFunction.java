package ro.go.adrhc.util.fn;

@FunctionalInterface
public interface ThrowableTriFunction<P1, P2, P3, R, T extends Throwable> {
	R apply(P1 p1, P2 p2, P3 p3) throws T;
}
