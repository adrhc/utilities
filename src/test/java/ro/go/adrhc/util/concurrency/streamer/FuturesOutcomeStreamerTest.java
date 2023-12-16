package ro.go.adrhc.util.concurrency.streamer;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.go.adrhc.util.streamer.FuturesOutcomeStreamer;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@ExtendWith(MockitoExtension.class)
@Slf4j
class FuturesOutcomeStreamerTest {
    private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();
    private static final ExecutorService FIXED = Executors.newFixedThreadPool(2);

    @Test
    void toStream() {
        Stream<String> strings = streamerStream(false);
        assertThat(strings).doesNotContain("value0", "value5");
    }

    @Test
    void streamClosure() {
        Optional<String> optional;
        try (Stream<String> stream = streamerStream(true)) {
            optional = stream.findFirst();
        }
        assertThat(optional).isPresent();
    }

    private static Stream<String> streamerStream(boolean cancelFuturesOnClose) {
        FuturesOutcomeStreamer<String> streamer =
                FuturesOutcomeStreamer.create(EXECUTOR, cancelFuturesOnClose);
        Stream<CompletableFuture<String>> futuresStream = IntStream.range(0, 10)
                .mapToObj(FuturesOutcomeStreamerTest::completableFuture);
        return streamer.toStream(futuresStream);
    }

    private static CompletableFuture<String> completableFuture(int i) {
        if (i % 5 == 0) {
            return CompletableFuture.failedFuture(new Exception("#error" + i));
        } else {
            return CompletableFuture.supplyAsync(() -> slow("value" + i), FIXED);
        }
    }

    private static <T> T slow(T t) {
        await().pollDelay(Duration.ofMillis(250)).until(() -> true);
        return t;
    }
}