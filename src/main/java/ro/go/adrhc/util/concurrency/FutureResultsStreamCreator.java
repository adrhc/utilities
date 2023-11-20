package ro.go.adrhc.util.concurrency;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedTransferQueue;
import java.util.stream.Stream;

import static ro.go.adrhc.util.concurrency.ConcurrencyUtils.waitForAll;

/**
 * not thread-safe but reusable in the same thread
 */
@Slf4j
@RequiredArgsConstructor
public class FutureResultsStreamCreator extends AbstractStreamCreator {
	private final ExecutorService adminExecutorService;

	public <T> Stream<T> create(Stream<CompletableFuture<T>> futures) {
		Stream<CompletableFuture<?>> voidFutures = attachFuturesOutcomeCollector(futures);
		asyncWaitForFuturesOutcome(queue, voidFutures);
		return toStream();
	}

	protected Stream<CompletableFuture<?>> attachFuturesOutcomeCollector(
			Stream<? extends CompletableFuture<?>> futures) {
		return futures
				.map(cf -> cf.whenComplete(this::collectFutureOutcome));
	}

	protected void collectFutureOutcome(Object t, Throwable e) {
		if (t != null) {
			queue.put(t);
		}
	}

	protected void asyncWaitForFuturesOutcome(
			LinkedTransferQueue<Object> queue, Stream<CompletableFuture<?>> futures) {
		adminExecutorService.execute(() -> waitForFuturesOutcome(queue, futures));
	}

	protected void waitForFuturesOutcome(
			LinkedTransferQueue<Object> queue, Stream<CompletableFuture<?>> futures) {
		waitForAll(futures);
		queueStopMarker();
	}
}
