package ro.go.adrhc.util.concurrency;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.go.adrhc.util.ObjectUtils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedTransferQueue;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static ro.go.adrhc.util.concurrency.ConcurrencyUtils.waitForAll;

/**
 * not thread-safe but reusable in the same thread
 */
@Slf4j
@RequiredArgsConstructor
public class AsyncStream<T> {
	private final LinkedTransferQueue<Object> queue = new LinkedTransferQueue<>();
	private final ExecutorService adminExecutorService;

	public Stream<T> toStream(Function<Consumer<T>, Stream<CompletableFuture<?>>> futuresProvider) {
		Stream<CompletableFuture<?>> futures = futuresProvider.apply(queue::put);
		return toStream(futures);
	}

	protected Stream<T> toStream(Stream<CompletableFuture<?>> futures) {
		signalProcessingCompletion(queue, futures);
		return Stream.generate(() -> null)
				.map(it -> safelyTake(queue, this))
				.takeWhile(it -> it != this)
				.map(ObjectUtils::cast);
	}

	protected void signalProcessingCompletion(
			LinkedTransferQueue<Object> queue, Stream<CompletableFuture<?>> futures) {
		adminExecutorService.execute(() -> doSignalProcessingCompletion(queue, futures));
	}

	protected void doSignalProcessingCompletion(
			LinkedTransferQueue<Object> queue, Stream<CompletableFuture<?>> futures) {
		waitForAll(futures);
		queue.put(this);
	}

	private Object safelyTake(LinkedTransferQueue<?> queue, Object defaultValue) {
		try {
			return queue.take();
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		}
		return defaultValue;
	}
}
