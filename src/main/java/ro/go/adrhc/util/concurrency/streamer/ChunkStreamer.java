package ro.go.adrhc.util.concurrency.streamer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.go.adrhc.util.ObjectUtils;

import java.util.Optional;
import java.util.concurrent.LinkedTransferQueue;
import java.util.stream.Stream;

/**
 * Chunk definition: all queue elements till CHUNK_END.
 */
@RequiredArgsConstructor
@Slf4j
public class ChunkStreamer {
	private static final Object CHUNK_END = ChunkStreamer.class;
	private final LinkedTransferQueue<Object> queue = new LinkedTransferQueue<>();

	/**
	 * Multiple Stream(s) created with this method might concurrently consume from queue!
	 * A consumer using Stream.findAny() might leave the rest of the chunk for the next
	 * consumer which might be wrong if the next consumer expect to consumer a full new chunk!
	 */
	public <T> Stream<T> streamChunk() {
		return Stream.generate(() -> null)
				.map(it -> takeElement().orElse(CHUNK_END))
				.takeWhile(it -> it != CHUNK_END)
				.map(ObjectUtils::cast);
	}

	public void addElement(Object t) {
		queue.put(t);
	}

	public void markChunkEnd() {
		queue.put(CHUNK_END);
	}

	/**
	 * remove current chunk's elements
	 */
	public void clear() {
		queue.clear();
	}

	protected Optional<Object> takeElement() {
		try {
			return Optional.of(queue.take());
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		}
		return Optional.empty();
	}
}
