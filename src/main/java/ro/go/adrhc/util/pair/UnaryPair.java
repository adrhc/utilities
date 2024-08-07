package ro.go.adrhc.util.pair;

public class UnaryPair<U> extends Pair<U, U> {
	public UnaryPair(U left, U right) {
		super(left, right);
	}

	public static <U> UnaryPair<U> empty() {
		return new UnaryPair<>(null, null);
	}

	public static <U> UnaryPair<U> ofLeft(U left) {
		return new UnaryPair<>(left, null);
	}

	public static <U> UnaryPair<U> ofRight(U right) {
		return new UnaryPair<>(null, right);
	}
}
