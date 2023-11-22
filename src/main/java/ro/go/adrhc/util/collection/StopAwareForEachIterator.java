package ro.go.adrhc.util.collection;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class StopAwareForEachIterator<T, E extends Exception>
		implements ForEachStoppableIterator<T, E> {
	private boolean stopped;

	public void stop() {
		this.stopped = true;
	}
}
