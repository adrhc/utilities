package ro.go.adrhc.util.collection;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

@RequiredArgsConstructor
@Getter
public abstract class SimpleStoppableVisitable<T> implements StoppableVisitable<T> {
	private boolean stopped;

	public static <T> SimpleStoppableVisitable<T> stopIgnoring(Visitable<T> visitable) {
		return new SimpleStoppableVisitable<T>() {
			@Override
			public void accept(Consumer<? super T> visitor) {
				visitable.accept(visitor);
			}
		};
	}

	@Override
	public void stop() {
		this.stopped = true;
	}
}
