package ro.go.adrhc.util.pair;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class PairsCollection<L, R> {
	private final List<Pair<L, R>> pairs;

	public static <L, R> PairsCollection<L, R> empty() {
		return new PairsCollection<>(new ArrayList<>());
	}

	public void add(Pair<L, R> pair) {
		pairs.add(pair);
	}

	public void addAll(PairsCollection<L, R> pairsCollection) {
		pairs.addAll(pairsCollection.pairs);
	}

	public Optional<R> getRightByLeftRef(L leftRef) {
		return pairs.stream().filter(p -> p.left() == leftRef).map(Pair::right).findAny();
	}

	public HashMap<L, R> toHashMap() {
		HashMap<L, R> map = new HashMap<>(pairs.size());
		pairs.forEach(p -> map.put(p.left(), p.right()));
		return map;
	}

	public List<L> getLefts() {
		return pairs.stream().map(Pair::left).toList();
	}

	public List<R> getRights() {
		return pairs.stream().map(Pair::right).toList();
	}
}
