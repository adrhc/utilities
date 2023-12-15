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
public class Pair<K, V> {
    protected final K key;
    protected final V value;

    public static <K, V> Pair<K, V> ofMapEntry(Map.Entry<K, V> mapEntry) {
        return new Pair<>(mapEntry.getKey(), mapEntry.getValue());
    }

    public static <K, V> Function<K, Pair<K, V>> ofValueFactory(Function<K, V> valueFactory) {
        return key -> new Pair<>(key, valueFactory.apply(key));
    }

    public <R> Pair<K, R> transformValue(Function<V, R> valueTransformer) {
        return new Pair<>(this.key, valueTransformer.apply(this.value));
    }

    public boolean hasValue() {
        return value != null;
    }
}
