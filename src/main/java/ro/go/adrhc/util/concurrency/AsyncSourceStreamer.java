package ro.go.adrhc.util.concurrency;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.go.adrhc.util.ObjectUtils;

import java.util.Optional;
import java.util.concurrent.LinkedTransferQueue;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Slf4j
public class AsyncSourceStreamer {
	private final LinkedTransferQueue<Object> queue = new LinkedTransferQueue<>();

	protected <T> Stream<T> streamElements() {
		return Stream.generate(() -> null)
				.map(it -> takeElement().orElse(this))
				.takeWhile(it -> it != this)
				.map(ObjectUtils::cast);
	}

	protected Optional<Object> takeElement() {
		try {
			return Optional.of(queue.take());
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		}
		return Optional.empty();
	}

	protected void addElement(Object t) {
		if (t != null) {
			queue.put(t);
		}
	}

	protected void markStreamEnd() {
		queue.put(this);
	}
}
