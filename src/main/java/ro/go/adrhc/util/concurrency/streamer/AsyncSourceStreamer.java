package ro.go.adrhc.util.concurrency.streamer;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ro.go.adrhc.util.collection.CloseAwareStream;
import ro.go.adrhc.util.collection.ForEachStoppableIterator;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.stream.Stream;

import static ro.go.adrhc.util.ThrowUtils.throwIf;

@RequiredArgsConstructor
@Slf4j
public class AsyncSourceStreamer<T> {
	private final ExecutorService executorService;

	public Stream<T> toStream(ForEachStoppableIterator<T, IOException> forEachStoppableIterator) {
		CloseAwareChunkStreamer chunkStreamer = new CloseAwareChunkStreamer(new ChunkStreamer());
		asyncCollect(chunkStreamer, forEachStoppableIterator);
		return new CloseAwareStream<>(chunkStreamer::close, chunkStreamer.streamChunk());
	}

	protected void asyncCollect(CloseAwareChunkStreamer chunkStreamer,
			ForEachStoppableIterator<T, IOException> forEachStoppableIterator) {
		executorService.execute(() -> collect(chunkStreamer, forEachStoppableIterator));
	}

	@SneakyThrows
	protected void collect(CloseAwareChunkStreamer chunkStreamer,
			ForEachStoppableIterator<T, IOException> forEachStoppableIterator) {
		try {
			forEachStoppableIterator.forEach(chunkStreamer::addElement);
		} catch (CloseAwareChunkStreamerException e) {
			log.debug("Elements adding are no longer allowed because stream closed!");
			return;
		}
		try {
			chunkStreamer.markChunkEnd();
		} catch (CloseAwareChunkStreamerException e) {
			log.debug("Chunk end not marked because stream closed!");
		}
	}

	@RequiredArgsConstructor
	private static class CloseAwareChunkStreamer implements AutoCloseable {
		private final ChunkStreamer chunkStreamer;
		private boolean closed;

		public void addElement(Object t) throws CloseAwareChunkStreamerException {
			throwIf(CloseAwareChunkStreamerException.INSTANCE, closed);
			chunkStreamer.addElement(t);
		}

		public void markChunkEnd() throws CloseAwareChunkStreamerException {
			throwIf(CloseAwareChunkStreamerException.INSTANCE, closed);
			chunkStreamer.markChunkEnd();
		}

		public <T> Stream<T> streamChunk() {
			return chunkStreamer.streamChunk();
		}

		public void close() {
			this.closed = true;
			chunkStreamer.clear();
		}
	}

	private static class CloseAwareChunkStreamerException extends IOException {
		public static final CloseAwareChunkStreamerException
				INSTANCE = new CloseAwareChunkStreamerException();
	}
}
