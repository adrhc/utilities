package ro.go.adrhc.util.collection;

import java.util.function.Consumer;

@FunctionalInterface
public interface Visitable<T> {
	void accept(Consumer<? super T> visitor);
}
