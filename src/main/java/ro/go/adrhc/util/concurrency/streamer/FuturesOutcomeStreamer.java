package ro.go.adrhc.util.concurrency.streamer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.go.adrhc.util.collection.SimpleStoppableVisitable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static ro.go.adrhc.util.concurrency.ConcurrencyUtils.waitAll;

@RequiredArgsConstructor
@Slf4j
public class FuturesOutcomeStreamer<T> {
	private final AsyncSourceStreamer<T> streamer;

	public static <T> FuturesOutcomeStreamer<T> create(ExecutorService executorService) {
		return new FuturesOutcomeStreamer<>(new AsyncSourceStreamer<>(executorService));
	}

	public Stream<T> toStream(Stream<? extends CompletableFuture<T>> futures) {
		return streamer.toStream(new FuturesStoppableVisitable<>(futures));
	}

	@RequiredArgsConstructor
	private static class FuturesStoppableVisitable<T> extends SimpleStoppableVisitable<T> {
		private final Stream<? extends CompletableFuture<T>> futures;

		public void accept(Consumer<? super T> elemCollector) {
			waitAll(attachFuturesOutcomeCollector(elemCollector, futures));
		}

		/*@Override
		public void stop() {
			super.stop();
			log.error("cancelling futures ...");
			futures.forEach(f -> f.cancel(true));
			log.error("futures cancelled");
		}*/

		protected Stream<CompletableFuture<T>> attachFuturesOutcomeCollector(
				Consumer<? super T> elemCollector, Stream<? extends CompletableFuture<T>> futures) {
			return futures.map(cf -> cf.handle((t, e) -> doHandle(elemCollector, t, e)));
		}

		protected T doHandle(Consumer<? super T> elemCollector, T t, Throwable e) {
			if (e != null) {
				log.error(e.getMessage(), e);
			} else {
				elemCollector.accept(t);
			}
			return t;
		}
	}
}
