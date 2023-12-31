package ro.go.adrhc.util.pair;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class PairsCollection<K, V> {
    private final List<Pair<K, V>> pairs;

    public static <K, V> PairsCollection<K, V> empty() {
        return new PairsCollection<>(new ArrayList<>());
    }

    public void add(Pair<K, V> pair) {
        pairs.add(pair);
    }

    public void addAll(PairsCollection<K, V> pairsCollection) {
        pairs.addAll(pairsCollection.pairs);
    }

    public Optional<V> getValueByKeyRef(K keyRef) {
        return pairs.stream().filter(p -> p.key() == keyRef).map(Pair::value).findAny();
    }

    public List<K> getKeys() {
        return pairs.stream().map(Pair::key).toList();
    }

    public List<V> getValues() {
        return pairs.stream().map(Pair::value).toList();
    }

    public HashMap<K, V> toHashMap() {
        HashMap<K, V> map = new HashMap<>(pairs.size());
        pairs.forEach(p -> map.put(p.key(), p.value()));
        return map;
    }
}
