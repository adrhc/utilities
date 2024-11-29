package ro.go.adrhc.util.streamer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.go.adrhc.util.io.SimpleDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Slf4j
public class PathsStoppableVisitable extends AbstractVisitorAwareStoppableVisitable<Path> {
	private final SimpleDirectory simpleDirectory;
	private final Path startPath;

	@Override
	public void accept(Consumer<? super Path> elemCollector) {
		this.visitor = elemCollector;
		try {
			simpleDirectory.doWithPathStream(startPath, this::collectPaths);
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
