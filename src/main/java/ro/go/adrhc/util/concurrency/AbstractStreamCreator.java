package ro.go.adrhc.util.concurrency;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.go.adrhc.util.ObjectUtils;

import java.util.concurrent.LinkedTransferQueue;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractStreamCreator {
	protected final LinkedTransferQueue<Object> queue = new LinkedTransferQueue<>();

	protected <T> Stream<T> toStream() {
		return Stream.generate(() -> null)
				.map(it -> safelyTake(queue, this))
				.takeWhile(it -> it != this)
				.map(ObjectUtils::cast);
	}

	protected Object safelyTake(LinkedTransferQueue<?> queue, Object defaultValue) {
		try {
			return queue.take();
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		}
		return defaultValue;
	}

	protected void queueStopMarker() {
		queue.put(this);
	}
}
