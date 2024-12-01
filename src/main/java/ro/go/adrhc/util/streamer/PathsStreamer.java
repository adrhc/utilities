package ro.go.adrhc.util.streamer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.go.adrhc.util.io.SimpleDirectory;

import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Slf4j
public class PathsStreamer {
	private final VisitableStructureStreamer<Path> streamer;
	private final SimpleDirectory simpleDirectory;

	public static PathsStreamer create(ExecutorService executorService, SimpleDirectory directory) {
		return new PathsStreamer(new VisitableStructureStreamer<>(executorService), directory);
	}

	/**
	 * This method must be used within a try-with-resources statement or similar control structure to ensure that the stream's open directory is closed promptly after the stream's operations have completed.
	 */
	public Stream<Path> toStream() {
		return toStream(simpleDirectory.getRoot());
	}

	/**
	 * This method must be used within a try-with-resources statement or similar control structure to ensure that the stream's open directory is closed promptly after the stream's operations have completed.
	 */
	public Stream<Path> toStream(Path startPath) {
		return streamer.toStream(new PathsStoppableVisitable(simpleDirectory, startPath));
	}

	public Path getRoot() {
		return simpleDirectory.getRoot();
	}
}
