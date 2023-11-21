package ro.go.adrhc.util.io;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.go.adrhc.util.concurrency.streamer.FuturesOutcomeStreamer;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Slf4j
public class FilesMetadataLoader<M> {
	private final ExecutorService metadataExecutorService;
	private final FuturesOutcomeStreamer futuresOutcomeStreamer;
	private final PathsStreamer pathsStreamer;
	private final Function<Path, M> metadataLoader;

	/**
	 * @param adminExecutorService    is used to join the CompletableFuture<M> collection
	 * @param metadataExecutorService is used to load the metadata from files
	 */
	public static <M> FilesMetadataLoader<M> create(
			ExecutorService adminExecutorService, ExecutorService metadataExecutorService,
			SimpleDirectory directory, Function<Path, M> metadataLoader) {
		return new FilesMetadataLoader<>(metadataExecutorService,
				FuturesOutcomeStreamer.create(adminExecutorService),
				PathsStreamer.create(metadataExecutorService, directory),
				metadataLoader);
	}

	public Stream<M> loadByPaths(Stream<Path> ids) {
		return ids.map(pathsStreamer::toStream).flatMap(this::loadPaths);
	}

	public Stream<M> loadAll() {
		return loadPaths(pathsStreamer.toStream());
	}

	protected Stream<M> loadPaths(Stream<Path> filePaths) {
		Stream<CompletableFuture<M>> futures = filePaths.map(this::loadMetadata);
		return futuresOutcomeStreamer.toStream(futures);
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
