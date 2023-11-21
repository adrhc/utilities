package ro.go.adrhc.util.io;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.go.adrhc.util.concurrency.FuturesOutcomeStreamCollector;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Slf4j
public class FilesMetadataLoader<M> {
	private final ExecutorService metadataExecutorService;
	private final FuturesOutcomeStreamCollector futuresOutcomeStreamCollector;
	private final DirectoryPathsStreamCollector pathsStreamCreator;
	private final Function<Path, M> metadataLoader;

	public static <M> FilesMetadataLoader<M> create(
			ExecutorService adminExecutorService, ExecutorService metadataExecutorService,
			SimpleDirectory directory, Function<Path, M> metadataLoader) {
		return new FilesMetadataLoader<>(metadataExecutorService,
				new FuturesOutcomeStreamCollector(adminExecutorService),
				new DirectoryPathsStreamCollector(metadataExecutorService, directory),
				metadataLoader);
	}

	public Stream<M> loadByPaths(Stream<Path> ids) {
		return ids.map(pathsStreamCreator::toStream).flatMap(this::loadPaths);
	}

	public Stream<M> loadAll() {
		return loadPaths(pathsStreamCreator.toStream());
	}

	protected Stream<M> loadPaths(Stream<Path> filePaths) {
		Stream<CompletableFuture<M>> futures = filePaths.map(this::loadMetadata);
		return futuresOutcomeStreamCollector.toStream(futures);
	}

	protected CompletableFuture<M> loadMetadata(Path filePath) {
		return CompletableFuture.supplyAsync(() ->
				doLoadMetadata(filePath), metadataExecutorService);
	}

	protected M doLoadMetadata(Path path) {
		try {
			return metadataLoader.apply(path);
		} finally {
			log.debug("\nmetadata loaded for: {}", path);
		}
	}
}
