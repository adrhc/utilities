package ro.go.adrhc.util.io;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.go.adrhc.util.concurrency.CompletableFuturesToOutcomeStreamConverter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Slf4j
public class FilesMetadataLoader<M> {
	private final ExecutorService metadataExecutorService;
	private final CompletableFuturesToOutcomeStreamConverter futuresToStreamConverter;
	private final SimpleDirectory filesDirectory;
	private final Function<Path, M> metadataLoader;

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
		return toFileMetadataStream(filesDirectory.getPaths(startPath));
	}

	protected Stream<M> toFileMetadataStream(Collection<Path> filePaths) {
		return futuresToStreamConverter
				.toStream(filePaths.stream().map(this::loadMetadata));
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
