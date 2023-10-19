package ro.go.adrhc.util.io;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.nio.file.CopyOption;
import java.nio.file.FileVisitOption;
import java.nio.file.Path;
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

	public static SimpleDirectory of(FileSystemUtils fsUtils,
			Supplier<Path> rootPathSupplier, Predicate<Path> pathsFilter) {
		return new SimpleDirectory(FOLLOW_LINKS, fsUtils, rootPathSupplier, pathsFilter);
	}

	public <R> R transformPathsStream(Function<Stream<Path>, R> pathsStreamProcessor) {
		return transformPathsStream(rootPathSupplier.get(), pathsStreamProcessor);
	}

	@SneakyThrows
	public <R> R transformPathsStream(Path start, Function<Stream<Path>, R> pathsStreamProcessor) {
		try (Stream<Path> paths = fsUtils.walk(resolvePath(start), fileVisitOption)) {
			return pathsStreamProcessor.apply(paths.filter(pathsFilter));
		}
	}

	public void doWithPathsStream(Consumer<Stream<Path>> pathsStreamConsumer) {
		doWithPathsStream(rootPathSupplier.get(), pathsStreamConsumer);
	}

	@SneakyThrows
	public void doWithPathsStream(Path start, Consumer<Stream<Path>> pathsStreamConsumer) {
		try (Stream<Path> paths = fsUtils.walk(resolvePath(start), fileVisitOption)) {
			pathsStreamConsumer.accept(paths.filter(pathsFilter));
		}
	}

	@SneakyThrows
	public Path cp(Path source, Path destination, CopyOption... options) {
		source = resolvePath(source);
		destination = resolvePath(destination);
		fsUtils.copy(source, destination, options);
		return destination;
	}

	@SneakyThrows
	public Path mv(Path source, Path destination, CopyOption... options) {
		source = resolvePath(source);
		destination = resolvePath(destination);
		fsUtils.move(source, destination, options);
		return destination;
	}

	@SneakyThrows
	public boolean rm(Path path) {
		return fsUtils.deleteIfExists(resolvePath(path));
	}

	public Path resolvePath(Path path) {
		return rootPathSupplier.get().resolve(path);
	}
}
