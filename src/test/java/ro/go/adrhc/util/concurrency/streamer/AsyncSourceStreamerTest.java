package ro.go.adrhc.util.concurrency.streamer;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@Slf4j
class AsyncSourceStreamerTest {
	@SneakyThrows
	public static void unsafeSleep(long millis) {
		Thread.sleep(500);
	}

	@Test
	void toStream() {
		Stream<String> stream = streamerStream();

		assertThat(stream.count()).isEqualTo(3);
	}

	@Test
	void closeBeforeCompletingPopulation() {
		Optional<String> optional;
		log.info("before stream creation");
		try (Stream<String> stream = streamerStream()) {
			log.info("after stream creation");
			optional = stream.findAny();
			log.info("before stream closing");
		}
		log.info("after stream closing");
		assertThat(optional).hasValue("value1");
	}

	private static Stream<String> streamerStream() {
		AsyncSourceStreamer<String> streamer =
				new AsyncSourceStreamer<>(Executors.newSingleThreadExecutor());

		return streamer.toStream(collector -> {
			log.info("adding value1 ...");
			collector.accept("value1");
			unsafeSleep(500);
			log.info("adding value2 ...");
			collector.accept("value2");
			unsafeSleep(500);
			log.info("adding value3 ...");
			collector.accept("value3");
		});
	}
}