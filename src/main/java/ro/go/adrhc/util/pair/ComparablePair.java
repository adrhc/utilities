package ro.go.adrhc.util.pair;

import ro.go.adrhc.util.ComparisonUtils;

import java.util.Objects;

public class ComparablePair<T1 extends Comparable<T1>, T2 extends Comparable<T2>>
		extends Pair<T1, T2> implements Comparable<Pair<T1, T2>> {
	public ComparablePair(T1 key, T2 value) {
		super(key, value);
	}

	@Override
	public int compareTo(Pair<T1, T2> another) {
		if (!Objects.equals(this.first(), another.first())) {
			return ComparisonUtils.compareComparable(this.first(), another.first());
		}
		return ComparisonUtils.compareComparable(this.second(), another.second());
	}
}
