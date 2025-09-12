package ro.go.adrhc.util.pair;

import lombok.experimental.Accessors;
import ro.go.adrhc.util.fn.BooleanInFunction;

import java.util.function.Function;

@Accessors(fluent = true)
public record HalfBooleanPair<R>(boolean left, R right) {
	public static <R> HalfBooleanPair<R> ofTrue(R right) {
		return new HalfBooleanPair<>(true, right);
	}

	public static <R> HalfBooleanPair<R> ofFalse(R right) {
		return new HalfBooleanPair<>(false, right);
	}

	public static <R> BooleanInFunction<HalfBooleanPair<R>>
	ofRightFactory(BooleanInFunction<R> rightFactory) {
		return left -> new HalfBooleanPair<>(left, rightFactory.apply(left));
	}

	public <T> Pair<T, R> transformLeft(BooleanInFunction<T> leftTransformer) {
		return new Pair<>(leftTransformer.apply(this.left), this.right);
	}

	public <T> HalfBooleanPair<T> transformRight(Function<R, T> rightTransformer) {
		return new HalfBooleanPair<>(this.left, rightTransformer.apply(this.right));
	}

	public boolean hasRight() {
		return right != null;
	}
}
