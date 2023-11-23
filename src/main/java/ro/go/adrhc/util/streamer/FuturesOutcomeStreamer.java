package ro.go.adrhc.util.streamer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.go.adrhc.util.collection.visitable.AbstractStoppableVisitable;

import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static ro.go.adrhc.util.concurrency.ConcurrencyUtils.waitAll;

@RequiredArgsConstructor
@Slf4j
public class FuturesOutcomeStreamer<T> {
	private final VisitableStructureStreamer<T> streamer;
	private final boolean cancelFuturesOnStreamClose;

	public static <T> FuturesOutcomeStreamer<T> create(ExecutorService executorService) {
		return new FuturesOutcomeStreamer<>(
				new VisitableStructureStreamer<>(executorService), false);
	}

	public static <T> FuturesOutcomeStreamer<T> create(
			ExecutorService executorService, boolean cancelFuturesOnStreamClose) {
		return new FuturesOutcomeStreamer<>(
				new VisitableStructureStreamer<>(executorService), cancelFuturesOnStreamClose);
	}

	public Stream<T> toStream(Stream<? extends CompletableFuture<T>> futures) {
		return streamer.toStream(new FuturesStoppableVisitable(futures));
	}

	@RequiredArgsConstructor
	private class FuturesStoppableVisitable extends AbstractStoppableVisitable<T> {
		private final Stream<? extends CompletableFuture<T>> futures;
		private List<? extends CompletableFuture<T>> futureList;

		public void accept(Consumer<? super T> elemCollector) {
			if (FuturesOutcomeStreamer.this.cancelFuturesOnStreamClose) {
				futureList = futures.toList();
			}
			waitAll(attachFuturesOutcomeCollector(elemCollector,
					FuturesOutcomeStreamer.this.cancelFuturesOnStreamClose
							? futureList.stream() : futures));
		}

		@Override
		public void stop() {
			super.stop();
			if (FuturesOutcomeStreamer.this.cancelFuturesOnStreamClose) {
//				log.info("cancelling futures ...");
				futureList.forEach(f -> f.cancel(true));
//				log.info("futures cancelled");
			}
		}

		protected Stream<CompletableFuture<T>> attachFuturesOutcomeCollector(
				Consumer<? super T> elemCollector, Stream<? extends CompletableFuture<T>> futures) {
			return futures.map(cf -> cf.handle((t, e) -> doHandle(elemCollector, t, e)));
		}

		protected T doHandle(Consumer<? super T> elemCollector, T t, Throwable e) {
			if (e != null) {
				if (!(e instanceof CancellationException)) {
					log.error(e.getMessage(), e);
				}
			} else {
				elemCollector.accept(t);
			}
			return t;
		}
	}
}
