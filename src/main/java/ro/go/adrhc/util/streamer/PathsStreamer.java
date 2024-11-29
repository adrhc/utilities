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

	public Stream<Path> toStream() {
		return toStream(simpleDirectory.getRoot());
	}

	public Stream<Path> toStream(Path startPath) {
		return streamer.toStream(new PathsStoppableVisitable(simpleDirectory, startPath));
	}
}
