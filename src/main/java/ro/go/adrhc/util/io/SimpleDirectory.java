package ro.go.adrhc.util.io;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.FileVisitOption;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.nio.file.FileVisitOption.FOLLOW_LINKS;

@RequiredArgsConstructor
public class SimpleDirectory {
	protected final FileVisitOption fileVisitOption;
	protected final FileSystemUtils fsUtils;
	protected final Supplier<Path> rootPathSupplier;
	protected final Predicate<Path> pathsFilter;

	/**
	 * Using no paths filter!
	 */
	public static SimpleDirectory fixedRootPath(Path path) {
		return fixedRootPath(path, p -> true);
	}

	public static SimpleDirectory fixedRootPath(Path path, Predicate<Path> pathsFilter) {
		return of(new FileSystemUtils(), () -> path, pathsFilter);
	}

	public static SimpleDirectory of(FileSystemUtils fsUtils,
			Supplier<Path> rootPathSupplier, Predicate<Path> pathsFilter) {
		return new SimpleDirectory(FOLLOW_LINKS, fsUtils, rootPathSupplier, pathsFilter);
	}

	public <R> R transformPathsStream(Function<Stream<Path>, R> pathsStreamProcessor) throws IOException {
		return transformPathsStream(rootPathSupplier.get(), pathsStreamProcessor);
	}

	public <R> R transformPathsStream(Path start, Function<Stream<Path>, R> pathsStreamProcessor) throws IOException {
		try (Stream<Path> paths = fsUtils.walk(resolvePath(start), fileVisitOption)) {
			return pathsStreamProcessor.apply(paths.filter(pathsFilter));
		}
	}

	public void doWithPathsStream(Consumer<Stream<Path>> pathsStreamConsumer) throws IOException {
		doWithPathsStream(rootPathSupplier.get(), pathsStreamConsumer);
	}

	public void doWithPathsStream(Path start, Consumer<Stream<Path>> pathsStreamConsumer) throws IOException {
		try (Stream<Path> paths = fsUtils.walk(resolvePath(start), fileVisitOption)) {
			pathsStreamConsumer.accept(paths.filter(pathsFilter));
		}
	}

	public Path cp(Path source, Path destination, CopyOption... options) throws IOException {
		source = resolvePath(source);
		destination = resolvePath(destination);
		fsUtils.copy(source, destination, options);
		return destination;
	}

	public Path mv(Path source, Path destination, CopyOption... options) throws IOException {
		source = resolvePath(source);
		destination = resolvePath(destination);
		fsUtils.move(source, destination, options);
		return destination;
	}

	public boolean rm(Path path) throws IOException {
		return fsUtils.deleteIfExists(resolvePath(path));
	}

	public Path resolvePath(Path path) {
		return rootPathSupplier.get().resolve(path);
	}

	public List<Path> getAllPaths() throws IOException {
		return getPaths(getRoot());
	}

	/**
	 * @param start is excluded from the result
	 */
	public List<Path> getPaths(Path start) throws IOException {
		return transformPathsStream(start, Stream::toList);
	}

	public Path getRoot() {
		return rootPathSupplier.get();
	}
}
