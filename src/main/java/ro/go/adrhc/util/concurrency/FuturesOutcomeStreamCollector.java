package ro.go.adrhc.util.concurrency;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Stream;

import static ro.go.adrhc.util.concurrency.ConcurrencyUtils.waitAll;

/**
 * not thread-safe but reusable in the same thread
 */
@Slf4j
@RequiredArgsConstructor
public class FuturesOutcomeStreamCollector extends AbstractStreamCreator {
	private final ExecutorService adminExecutorService;

	public <T> Stream<T> toStream(Stream<CompletableFuture<T>> futures) {
		asyncWaitAll(attachFuturesOutcomeCollector(futures));
		return receivedElementsStream();
	}

	protected Stream<CompletableFuture<?>> attachFuturesOutcomeCollector(
			Stream<? extends CompletableFuture<?>> futures) {
		return futures.map(cf -> cf.whenComplete((t, e) -> addElement(t)));
	}

	protected void asyncWaitAll(Stream<CompletableFuture<?>> futures) {
		adminExecutorService.execute(() -> waitAllAndSignalCollectionCompletion(futures));
	}

	protected void waitAllAndSignalCollectionCompletion(Stream<CompletableFuture<?>> futures) {
		waitAll(futures);
		elementsAddingCompleted();
	}
}
