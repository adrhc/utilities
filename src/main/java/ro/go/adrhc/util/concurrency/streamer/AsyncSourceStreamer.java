package ro.go.adrhc.util.concurrency.streamer;

import lombok.RequiredArgsConstructor;
import ro.go.adrhc.util.collection.ForEachIterator;

import java.util.concurrent.ExecutorService;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class AsyncSourceStreamer<T> {
	private final ExecutorService executorService;

	public Stream<T> toStream(ForEachIterator<T> forEachIterator) {
		ChunkStreamer queue = new ChunkStreamer();
		asyncCollect(queue, forEachIterator);
		return queue.streamChunk();
	}

	protected void asyncCollect(ChunkStreamer queue, ForEachIterator<T> forEachIterator) {
		executorService.execute(() -> collect(queue, forEachIterator));
	}

	protected void collect(ChunkStreamer queue, ForEachIterator<T> forEachIterator) {
		forEachIterator.forEach(queue::addElement);
		queue.markChunkEnd();
	}
}
