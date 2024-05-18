package ro.go.adrhc.util.collection.visitable;

import java.util.function.Consumer;

public interface StoppableVisitable<T> extends Visitable<T> {
	static <T> StoppableVisitable<T> ignoreStop(Visitable<T> visitable) {
		return new StoppableVisitable<>() {
			@Override
			public void stop() {
				// do nothing
			}

			@Override
			public void accept(Consumer<? super T> visitor) {
				visitable.accept(visitor);
			}
		};
	}

	void stop();
}
