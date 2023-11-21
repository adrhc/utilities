package ro.go.adrhc.util.collection;

import java.util.function.Consumer;

@FunctionalInterface
public interface ForEachIterator<T> {
	void forEach(Consumer<? super T> visitor);
}
