package ro.go.adrhc.util.io;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.go.adrhc.util.concurrency.AbstractStreamCreator;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Slf4j
public class DirectoryPathsStreamCreator extends AbstractStreamCreator {
	private final ExecutorService executorService;
	private final SimpleDirectory directory;

	public Stream<Path> create() {
		return create(directory.getRoot());
	}

	public Stream<Path> create(Path startPath) {
		asyncPathsCollect(startPath);
		return toStream();
	}

	protected void asyncPathsCollect(Path startPath) {
		executorService.execute(() -> collectPaths(startPath));
	}

	protected void collectPaths(Path startPath) {
		try {
			directory.doWithPathsStream(startPath, paths -> paths.forEach(queue::add));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		queueStopMarker();
	}
}
