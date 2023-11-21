package ro.go.adrhc.util.concurrency.streamer;

import lombok.RequiredArgsConstructor;
import ro.go.adrhc.util.collection.ForEachIterator;

import java.util.concurrent.ExecutorService;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class SimpleAsyncSourceStreamer<T> extends AsyncSourceStreamer {
	private final ExecutorService executorService;

	public Stream<T> toStream(ForEachIterator<T> forEachIterator) {
		asyncCollect(forEachIterator);
		return streamElements();
	}

	protected void asyncCollect(ForEachIterator<T> forEachIterator) {
		executorService.execute(() -> collect(forEachIterator));
	}

	protected void collect(ForEachIterator<T> forEachIterator) {
		forEachIterator.forEach(super::addElement);
		markStreamEnd();
	}
}
