package ro.go.adrhc.util.streamer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.go.adrhc.util.ObjectUtils;

import java.util.Optional;
import java.util.concurrent.LinkedTransferQueue;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Slf4j
public class QueueSliceStreamer {
	private static final Object CHUNK_END = QueueSliceStreamer.class;
	private final LinkedTransferQueue<Object> queue = new LinkedTransferQueue<>();

	/**
	 * Multiple Stream(s) created by this method might concurrently consume from queue!
	 * A consumer using Stream.findAny() might leave the rest of the chunk for the next
	 * consumer which might be wrong if the next consumer expects to consume a full new
	 * chunk instead of continuing from where the previous consumer left!
	 */
	public <T> Stream<T> streamCurrentSlice() {
		return Stream.iterate(null, it -> null)
				.map(it -> take().orElse(CHUNK_END))
				.takeWhile(it -> it != CHUNK_END)
				.map(ObjectUtils::cast);
	}

	/**
	 * see BlockingQueue#put
	 */
	public void put(Object t) {
		queue.put(t);
	}

	/**
	 * ends the current slice hence starting a new one
	 */
	public void startNewSlice() {
		queue.put(CHUNK_END);
	}

	/**
	 * clears the entire queue
	 */
	public void clear() {
		queue.clear();
	}

	/**
	 * returns empty when interrupted
	 */
	protected Optional<Object> take() {
		try {
			return Optional.of(queue.take());
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		}
		return Optional.empty();
	}
}
