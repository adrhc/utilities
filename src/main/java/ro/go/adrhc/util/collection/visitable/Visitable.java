package ro.go.adrhc.util.collection.visitable;

import java.util.function.Consumer;

@FunctionalInterface
public interface Visitable<T> {
	void accept(Consumer<? super T> visitor);
}
