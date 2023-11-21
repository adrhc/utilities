package ro.go.adrhc.util.io;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.go.adrhc.util.concurrency.streamer.SimpleAsyncSourceStreamer;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Slf4j
public class PathsStreamer {
	private final SimpleAsyncSourceStreamer<Path> streamer;
	private final SimpleDirectory directory;

	public static PathsStreamer create(ExecutorService executorService, SimpleDirectory directory) {
		return new PathsStreamer(new SimpleAsyncSourceStreamer<>(executorService), directory);
	}

	public Stream<Path> toStream() {
		return toStream(directory.getRoot());
	}

	public Stream<Path> toStream(Path startPath) {
		return streamer.toStream(elemCollector -> collectPaths(elemCollector, startPath));
	}

	protected void collectPaths(Consumer<? super Path> elemCollector, Path startPath) {
		try {
			directory.doWithPathsStream(startPath, paths -> paths.forEach(elemCollector));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		streamer.markStreamEnd();
	}
}
