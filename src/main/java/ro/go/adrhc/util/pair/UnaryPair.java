package ro.go.adrhc.util.pair;

public class UnaryPair<T> extends Pair<T, T> {
	public UnaryPair(T left, T right) {
		super(left, right);
	}

	public static <T> UnaryPair<T> ofLeft(T left) {
		return new UnaryPair<>(left, null);
	}
}
