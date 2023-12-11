package ro.go.adrhc.util.pair;

public class UnaryPair<T> extends Pair<T, T> {
    public UnaryPair(T key, T value) {
        super(key, value);
    }

    public static <T> UnaryPair<T> ofKey(T key) {
        return new UnaryPair<>(key, null);
    }
}
