package ro.go.adrhc.util.pair;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
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

	/**
	 * right will be null
	 */
	public static <L, R> Pair<L, R> ofLeft(L left) {
		return new Pair<>(left, null);
	}

	/**
	 * left will be null
	 */
	public static <L, R> Pair<L, R> ofRight(R right) {
		return new Pair<>(null, right);
	}

	public <T> Pair<T, R> mapLeft(Function<L, T> leftMapper) {
		return new Pair<>(leftMapper.apply(this.left), this.right);
	}

	public <T> Pair<L, T> mapRight(Function<R, T> rightMapper) {
		return new Pair<>(this.left, rightMapper.apply(this.right));
	}

	/**
	 * The right side will be null if the Optional<T> is empty!
	 */
	public <T> Pair<L, T> flatMapRight(Function<R, Optional<T>> rightMapper) {
		return new Pair<>(this.left, rightMapper.apply(this.right).orElse(null));
	}

	public void ifRightPresent(Consumer<R> consumer) {
		consumer.accept(right);
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
