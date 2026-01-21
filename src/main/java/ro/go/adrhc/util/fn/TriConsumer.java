package ro.go.adrhc.util.fn;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@FunctionalInterface
public interface TriConsumer<T1, T2, T3> {
	static <T1, T2, T3> BiConsumer<T1, T2> curry(
		TriConsumer<T1, T2, T3> triConsumer, T3 t3) {
		return (t1, t2) -> triConsumer.accept(t1, t2, t3);
	}

	static <T1, T2, T3> Consumer<T3> curryT1T2(
		TriConsumer<T1, T2, T3> triConsumer, T1 t1, T2 t2) {
		return t3 -> triConsumer.accept(t1, t2, t3);
	}

	static <T1, T2, T3> BiConsumer<T2, T3> curryT1(
		TriConsumer<T1, T2, T3> triConsumer, T1 t1) {
		return (t2, t3) -> triConsumer.accept(t1, t2, t3);
	}

	void accept(T1 k, T2 v, T3 s);
}
