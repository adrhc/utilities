package ro.go.adrhc.util.io;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.go.adrhc.util.concurrency.FutureResultsStreamCreator;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Slf4j
public class FilesMetadataLoader<M> {
	private final ExecutorService metadataExecutorService;
	private final FutureResultsStreamCreator futureResultsStreamCreator;
	private final SimpleDirectory filesDirectory;
	private final Function<Path, M> metadataLoader;

	public static <M> FilesMetadataLoader<M> create(
			ExecutorService adminExecutorService, ExecutorService metadataExecutorService,
			SimpleDirectory filesDirectory, Function<Path, M> metadataLoader) {
		return new FilesMetadataLoader<>(metadataExecutorService,
				new FutureResultsStreamCreator(adminExecutorService),
				filesDirectory, metadataLoader);
	}

	public Stream<M> loadByPaths(Stream<Path> ids) {
		return ids.flatMap(this::safelyLoadByPath);
	}

	public Stream<M> loadAll() throws IOException {
		return loadByStartPath(filesDirectory.getRoot());
	}

	protected Stream<M> safelyLoadByPath(Path startPath) {
		try {
			return loadByStartPath(startPath);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			return Stream.empty();
		}
	}

	protected Stream<M> loadByStartPath(Path startPath) throws IOException {
		List<Path> filePaths = filesDirectory.getPaths(startPath);
		Stream<CompletableFuture<M>> futures = filePaths.stream().map(this::loadMetadata);
		return futureResultsStreamCreator.create(futures);
	}

	protected CompletableFuture<M> loadMetadata(Path filePath) {
		return CompletableFuture.supplyAsync(() -> doLoadMetadata(filePath), metadataExecutorService);
	}

	protected M doLoadMetadata(Path path) {
		try {
			return metadataLoader.apply(path);
		} finally {
			log.debug("\nmetadata loaded for: {}", path);
		}
	}
}
