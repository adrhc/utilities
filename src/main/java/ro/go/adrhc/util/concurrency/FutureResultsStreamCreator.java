package ro.go.adrhc.util.concurrency;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
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
		asyncWaitForFuturesOutcome(attachFuturesOutcomeCollector(futures));
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

	protected void asyncWaitForFuturesOutcome(Stream<CompletableFuture<?>> futures) {
		adminExecutorService.execute(() -> waitForFuturesOutcome(futures));
	}

	protected void waitForFuturesOutcome(Stream<CompletableFuture<?>> futures) {
		waitForAll(futures);
		queueStopMarker();
	}
}
