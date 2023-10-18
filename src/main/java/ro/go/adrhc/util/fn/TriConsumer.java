package ro.go.adrhc.util.fn;

import java.util.function.Consumer;

@FunctionalInterface
public interface TriConsumer<K, V, S> {
	void accept(K k, V v, S s);

	static <T1, T2, T3> Consumer<T3> curryT1T2(
			TriConsumer<T1, T2, T3> triConsumer, T1 t1, T2 t2) {
		return t3 -> triConsumer.accept(t1, t2, t3);
	}
}
