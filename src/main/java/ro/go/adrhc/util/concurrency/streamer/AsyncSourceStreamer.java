package ro.go.adrhc.util.concurrency.streamer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.go.adrhc.util.ObjectUtils;

import java.util.Optional;
import java.util.concurrent.LinkedTransferQueue;
import java.util.stream.Stream;

/**
 * not thread-safe but reusable in the same thread
 */
@RequiredArgsConstructor
@Slf4j
public class AsyncSourceStreamer {
	private static final Object STREAM_END = AsyncSourceStreamer.class;
	private final LinkedTransferQueue<Object> queue = new LinkedTransferQueue<>();

	public <T> Stream<T> streamElements() {
		return Stream.generate(() -> null)
				.map(it -> takeElement().orElse(STREAM_END))
				.takeWhile(it -> it != STREAM_END)
				.map(ObjectUtils::cast);
	}

	public Optional<Object> takeElement() {
		try {
			return Optional.of(queue.take());
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		}
		return Optional.empty();
	}

	public void addElement(Object t) {
		queue.put(t);
	}

	public void markStreamEnd() {
		queue.put(STREAM_END);
	}
}
