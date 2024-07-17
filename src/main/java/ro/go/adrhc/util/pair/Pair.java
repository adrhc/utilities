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
public class Pair<T1, T2> {
	protected final T1 first;
	protected final T2 second;

	public static <T1, T2> Pair<T1, T2> ofMapEntry(Map.Entry<T1, T2> mapEntry) {
		return new Pair<>(mapEntry.getKey(), mapEntry.getValue());
	}

	public static <T1, T2> Function<T1, Pair<T1, T2>> ofValueFactory(
			Function<T1, T2> valueFactory) {
		return key -> new Pair<>(key, valueFactory.apply(key));
	}

	public <R> Pair<T1, R> transformValue(Function<T2, R> valueTransformer) {
		return new Pair<>(this.first, valueTransformer.apply(this.second));
	}

	public boolean hasValue() {
		return second != null;
	}
}
