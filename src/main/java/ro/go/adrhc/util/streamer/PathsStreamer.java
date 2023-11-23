package ro.go.adrhc.util.streamer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.go.adrhc.util.collection.visitable.AbstractStoppableVisitable;
import ro.go.adrhc.util.io.SimpleDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Slf4j
public class PathsStreamer {
	private final VisitableStructureStreamer<Path> streamer;
	private final SimpleDirectory directory;

	public static PathsStreamer create(ExecutorService executorService, SimpleDirectory directory) {
		return new PathsStreamer(new VisitableStructureStreamer<>(executorService), directory);
	}

	public Stream<Path> toStream() {
		return toStream(directory.getRoot());
	}

	public Stream<Path> toStream(Path startPath) {
		return streamer.toStream(new PathsStoppableVisitable(startPath));
	}

	@RequiredArgsConstructor
	private class PathsStoppableVisitable extends AbstractStoppableVisitable<Path> {
		private final Path startPath;

		@Override
		public void accept(Consumer<? super Path> elemCollector) {
			try {
				directory.doWithPathStream(startPath, paths -> collectPaths(elemCollector, paths));
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}

		private void collectPaths(Consumer<? super Path> elemCollector, Stream<Path> paths) {
			paths.takeWhile(p -> !this.isStopped()).forEach(elemCollector);
		}
	}
}
