package ro.go.adrhc.util.pair;

import ro.go.adrhc.util.ComparisonUtils;

import java.util.Objects;

public class ComparablePair<L extends Comparable<L>, R extends Comparable<R>>
	extends Pair<L, R> implements Comparable<Pair<L, R>> {
	public ComparablePair(L left, R right) {
		super(left, right);
	}

	@Override
	public int compareTo(Pair<L, R> another) {
		if (!Objects.equals(this.left(), another.left())) {
			return ComparisonUtils.compareComparable(this.left(), another.left());
		}
		return ComparisonUtils.compareComparable(this.right(), another.right());
	}
}
