package ro.go.adrhc.util.concurrency.streamer;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.go.adrhc.util.collection.SimpleStoppableVisitable;
import ro.go.adrhc.util.streamer.VisitableStructureStreamer;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class VisitableStructureStreamerTest {
	private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();

	@Test
	void toStream() {
		Stream<String> stream = streamerStream();

		assertThat(stream.count()).isEqualTo(3);
	}

	@Test
	void streamClosure() {
		Optional<String> optional;
		try (Stream<String> stream = streamerStream()) {
			optional = stream.findFirst();
		}
		assertThat(optional).hasValue("value1");
	}

	private static Stream<String> streamerStream() {
		VisitableStructureStreamer<String> streamer = new VisitableStructureStreamer<>(EXECUTOR);

		return streamer.toStream(new SimpleStoppableVisitable<>() {
			@Override
			public void accept(Consumer<? super String> visitor) {
				present(visitor, "value1");
				present(visitor, "value2");
				present(visitor, "value3");
			}

			private void present(Consumer<? super String> visitor, String value) {
				if (isStopped()) {
					log.info("skipping {} ...", value);
					return;
				}
				log.info("adding {} ...", value);
				visitor.accept(value);
				await().pollDelay(Duration.ofMillis(500)).until(() -> true);
			}
		});
	}
}