package ro.go.adrhc.util.pair;

import ro.go.adrhc.util.ComparisonUtils;

import java.util.Objects;

public class ComparablePair<K extends Comparable<K>, V extends Comparable<V>>
		extends Pair<K, V> implements Comparable<Pair<K, V>> {
	public ComparablePair(K key, V value) {
		super(key, value);
	}

	@Override
	public int compareTo(Pair<K, V> another) {
		if (!Objects.equals(this.key(), another.key())) {
			return ComparisonUtils.compareComparable(this.key(), another.key());
		}
		return ComparisonUtils.compareComparable(this.value(), another.value());
	}
}
