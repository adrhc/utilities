package ro.go.adrhc.util.concurrency.streamer;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@Slf4j
class FuturesOutcomeStreamerTest {
	@Test
	void toStream() {
		FuturesOutcomeStreamer streamer = FuturesOutcomeStreamer
				.create(Executors.newSingleThreadExecutor());
		Stream<CompletableFuture<?>> futuresStream = Stream.of(
				CompletableFuture.completedFuture("value1"),
				CompletableFuture.failedFuture(new Exception("#error")),
				CompletableFuture.completedFuture("value2"));
		Stream<String> strings = streamer.toStream(futuresStream);
		assertThat(strings).containsOnly("value1", "value2");
	}
}