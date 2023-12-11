package ro.go.adrhc.util.pair;

import lombok.experimental.UtilityClass;

import java.util.stream.Stream;

@UtilityClass
public class PairUtils {
    public static <K, V> PairsCollection<K, V> toPairsCollection(Stream<Pair<K, V>> pairs) {
        return pairs.collect(PairsCollection::empty, PairsCollection::add, PairsCollection::addAll);
    }
}
