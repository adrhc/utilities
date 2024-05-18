package ro.go.adrhc.util.streamer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static ro.go.adrhc.util.concurrency.FutureUtils.waitAll;

/**
 * Not thread safe!
 */
@RequiredArgsConstructor
@Slf4j
public class FuturesStoppableVisitable<T> extends AbstractVisitorAwareStoppableVisitable<T> {
	private final Stream<? extends CompletableFuture<T>> futures;
	private final List<? extends CompletableFuture<T>> cancellableFutures;

	public static <T> FuturesStoppableVisitable<T> create(
			Stream<? extends CompletableFuture<T>> futures, boolean cancelFuturesOnStreamClose) {
		if (cancelFuturesOnStreamClose) {
			List<? extends CompletableFuture<T>> futureList = futures.toList();
			return new FuturesStoppableVisitable<>(futureList.stream(), futureList);
		} else {
			return new FuturesStoppableVisitable<>(futures, null);
		}
	}

	public void accept(Consumer<? super T> elemCollector) {
		this.visitor = elemCollector;
		waitAll(attachFuturesOutcomeCollector());
	}

	protected Stream<CompletableFuture<T>> attachFuturesOutcomeCollector() {
		return futures.map(cf -> cf.handle(this::collectFutureOutcome));
	}

	protected T collectFutureOutcome(T t, Throwable e) {
		if (e != null) {
			if (!(e instanceof CancellationException)) {
				log.error(e.getMessage(), e);
			}
		} else {
			visitor.accept(t);
		}
		return t;
	}

	@Override
	public void stop() {
		super.stop();
		if (cancellableFutures != null) {
//			log.info("cancelling futures ...");
			cancellableFutures.forEach(f -> f.cancel(true));
//			log.info("futures cancelled");
		}
	}
}
