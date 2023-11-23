package ro.go.adrhc.util.streamer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Slf4j
public class FuturesOutcomeStreamer<T> {
	private final VisitableStructureStreamer<T> streamer;
	/**
	 * "true" requires that FuturesStoppableVisitable must
	 * cache the futures stream which is very inefficient!
	 */
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
		return streamer.toStream(FuturesStoppableVisitable
				.create(futures, cancelFuturesOnStreamClose));
	}
}
