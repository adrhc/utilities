package ro.go.adrhc.util.concurrency.streamer;

import com.rainerhahnekamp.sneakythrow.functional.SneakyConsumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.go.adrhc.util.ObjectUtils;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Stream;

import static ro.go.adrhc.util.concurrency.ConcurrencyUtils.waitAll;

@RequiredArgsConstructor
@Slf4j
public class FuturesOutcomeStreamer {
	private final AsyncSourceStreamer<Object> streamer;

	public static FuturesOutcomeStreamer create(ExecutorService executorService) {
		return new FuturesOutcomeStreamer(new AsyncSourceStreamer<>(executorService));
	}

	public <T> Stream<T> toStream(Stream<? extends CompletableFuture<?>> futures) {
		return streamer
				.toStream(elemCollector -> collectFuturesOutcome(elemCollector, futures))
				.map(ObjectUtils::cast);
	}

	protected void collectFuturesOutcome(
			SneakyConsumer<Object, IOException> elemCollector,
			Stream<? extends CompletableFuture<?>> futures) {
		waitAll(attachFuturesOutcomeCollector(elemCollector, futures));
	}

	protected Stream<CompletableFuture<?>> attachFuturesOutcomeCollector(
			SneakyConsumer<Object, IOException> elemCollector, Stream<? extends CompletableFuture<?>> futures) {
		return futures.map(cf -> cf.handle((t, e) -> doHandle(elemCollector, t, e)));
	}

	protected Object doHandle(SneakyConsumer<Object, IOException> elemCollector, Object t, Throwable e) {
		if (e == null) {
			try {
				elemCollector.accept(t);
			} catch (IOException ex) {
				log.error(ex.getMessage(), ex);
			}
		} else {
			log.error(e.getMessage(), e);
		}
		return t;
	}
}
