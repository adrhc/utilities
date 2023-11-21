package ro.go.adrhc.util.io;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.go.adrhc.util.concurrency.AsyncSourceStreamer;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Slf4j
public class PathsStreamer extends AsyncSourceStreamer {
	private final ExecutorService executorService;
	private final SimpleDirectory directory;

	public Stream<Path> toStream() {
		return toStream(directory.getRoot());
	}

	public Stream<Path> toStream(Path startPath) {
		asyncPathsCollect(startPath);
		return streamElements();
	}

	protected void asyncPathsCollect(Path startPath) {
		executorService.execute(() -> collectPaths(startPath));
	}

	protected void collectPaths(Path startPath) {
		try {
			directory.doWithPathsStream(startPath, paths -> paths.forEach(super::addElement));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		markStreamEnd();
	}
}
