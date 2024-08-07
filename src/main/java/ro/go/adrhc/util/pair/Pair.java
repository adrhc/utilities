package ro.go.adrhc.util.pair;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Map;
import java.util.function.Function;

@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
@Accessors(fluent = true)
public class Pair<L, R> {
	protected final L left;
	protected final R right;

	public static <L, R> Pair<L, R> emptyPair() {
		return new Pair<>(null, null);
	}

	public static <L, R> Pair<L, R> ofMapEntry(Map.Entry<L, R> mapEntry) {
		return new Pair<>(mapEntry.getKey(), mapEntry.getValue());
	}

	public static <L, R> Function<L, Pair<L, R>> ofRightFactory(
			Function<L, R> rightFactory) {
		return left -> new Pair<>(left, rightFactory.apply(left));
	}

	public <T> Pair<T, R> transformLeft(Function<L, T> leftTransformer) {
		return new Pair<>(leftTransformer.apply(this.left), this.right);
	}

	public <T> Pair<L, T> transformRight(Function<R, T> rightTransformer) {
		return new Pair<>(this.left, rightTransformer.apply(this.right));
	}

	public boolean hasLeft() {
		return left != null;
	}

	public boolean hasRight() {
		return right != null;
	}

	public boolean isEmpty() {
		return left == null && right == null;
	}
}
