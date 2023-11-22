package ro.go.adrhc.util.concurrency.streamer;

import com.rainerhahnekamp.sneakythrow.functional.SneakyConsumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.go.adrhc.util.io.SimpleDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.stream.Stream;

import static ro.go.adrhc.util.collection.IterableUtils.iterable;

@RequiredArgsConstructor
@Slf4j
public class PathsStreamer {
	private final AsyncSourceStreamer<Path> streamer;
	private final SimpleDirectory directory;

	public static PathsStreamer create(ExecutorService executorService, SimpleDirectory directory) {
		return new PathsStreamer(new AsyncSourceStreamer<>(executorService), directory);
	}

	public Stream<Path> toStream() {
		return toStream(directory.getRoot());
	}

	public Stream<Path> toStream(Path startPath) {
		return streamer.toStream(elemCollector -> collectPaths(elemCollector, startPath));

	}

	protected void collectPaths(SneakyConsumer<? super Path, IOException> elemCollector, Path startPath) {
		try {
			directory.doWithPathStream(startPath, paths -> doWithPathStream(elemCollector, paths));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	protected void doWithPathStream(
			SneakyConsumer<? super Path, IOException> elemCollector,
			Stream<Path> pathStream) throws IOException {
		for (Path p : iterable(pathStream)) {
			elemCollector.accept(p);
		}
	}
}
