package ro.go.adrhc.util.concurrency.streamer;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ro.go.adrhc.util.collection.CloseAwareStream;
import ro.go.adrhc.util.collection.StoppableVisitable;
import ro.go.adrhc.util.collection.Visitable;
import ro.go.adrhc.util.concurrency.LocksCollection;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Slf4j
public class AsyncSourceStreamer<T> {
	private final ExecutorService executorService;

	public Stream<T> toStream(StoppableVisitable<T> stoppableVisitable) {
		CloseAwareChunkStreamer chunkStreamer =
				CloseAwareChunkStreamer.create(stoppableVisitable::stop);
		asyncCollect(chunkStreamer, stoppableVisitable);
		return new CloseAwareStream<>(chunkStreamer::close, chunkStreamer.streamChunk());
	}

	protected void asyncCollect(CloseAwareChunkStreamer chunkStreamer, Visitable<T> visitable) {
		executorService.execute(() -> collect(chunkStreamer, visitable));
	}

	@SneakyThrows
	protected void collect(CloseAwareChunkStreamer chunkStreamer, Visitable<T> visitable) {
//		log.info("begin elements collection");
		visitable.accept(chunkStreamer::addElement);
//		log.info("elements collection completed");
		chunkStreamer.markChunkEnd();
//		log.info("end chunk marker added");
	}

	@RequiredArgsConstructor
	private static class CloseAwareChunkStreamer implements AutoCloseable {
		private final Lock addElementLock = new ReentrantLock();
		private final Lock markChunkEndLock = new ReentrantLock();
		private final LocksCollection locks = LocksCollection.of(addElementLock, markChunkEndLock);
		private final ChunkStreamer chunkStreamer;
		private final Runnable stopTrigger;
		private boolean closed;

		public static CloseAwareChunkStreamer create(Runnable stopTrigger) {
			return new CloseAwareChunkStreamer(new ChunkStreamer(), stopTrigger);
		}

		public void addElement(Object t) {
			doIfNotClosed(addElementLock, () -> chunkStreamer.addElement(t));
		}

		public void markChunkEnd() {
			doIfNotClosed(markChunkEndLock, chunkStreamer::markChunkEnd);
		}

		public <T> Stream<T> streamChunk() {
			return chunkStreamer.streamChunk();
		}

		public void close() {
//			log.info("closing ...");
			locks.execute(this::doClose);
//			log.info("closed");
		}

		public void doClose() {
			this.closed = true;
			chunkStreamer.clear();
			stopTrigger.run();
		}

		private void doIfNotClosed(Lock lock, Runnable runnable) {
			if (this.closed) {
				return;
			}
			lock.lock();
			try {
				if (!this.closed) {
					runnable.run();
				}
			} finally {
				lock.unlock();
			}
		}
	}
}
