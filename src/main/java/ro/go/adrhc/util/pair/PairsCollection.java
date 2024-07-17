package ro.go.adrhc.util.pair;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class PairsCollection<T1, T2> {
	private final List<Pair<T1, T2>> pairs;

	public static <T1, T2> PairsCollection<T1, T2> empty() {
		return new PairsCollection<>(new ArrayList<>());
	}

	public void add(Pair<T1, T2> pair) {
		pairs.add(pair);
	}

	public void addAll(PairsCollection<T1, T2> pairsCollection) {
		pairs.addAll(pairsCollection.pairs);
	}

	public Optional<T2> getValueByKeyRef(T1 keyRef) {
		return pairs.stream().filter(p -> p.first() == keyRef).map(Pair::second).findAny();
	}

	public HashMap<T1, T2> toHashMap() {
		HashMap<T1, T2> map = new HashMap<>(pairs.size());
		pairs.forEach(p -> map.put(p.first(), p.second()));
		return map;
	}

	public List<T1> getKeys() {
		return pairs.stream().map(Pair::first).toList();
	}

	public List<T2> getValues() {
		return pairs.stream().map(Pair::second).toList();
	}
}
