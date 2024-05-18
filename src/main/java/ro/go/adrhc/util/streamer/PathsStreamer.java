package ro.go.adrhc.util.streamer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
	private class PathsStoppableVisitable extends AbstractVisitorAwareStoppableVisitable<Path> {
		private final Path startPath;

		@Override
		public void accept(Consumer<? super Path> elemCollector) {
			this.visitor = elemCollector;
			try {
				directory.doWithPathStream(startPath, this::collectPaths);
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}

		private void collectPaths(Stream<Path> paths) {
			try {
				paths.forEach(this::collectPath);
			} catch (CollectionStoppedException e) {
				// do nothing
			}
		}

		private void collectPath(Path path) {
			if (this.isStopped()) {
//				log.info("\ncollection stop detected");
				throw new CollectionStoppedException();
			}
//			log.info("\ncollected {}", path);
			visitor.accept(path);
		}

		private static class CollectionStoppedException extends RuntimeException {
		}
	}
}
