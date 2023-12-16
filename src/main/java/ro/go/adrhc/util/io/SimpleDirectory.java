package ro.go.adrhc.util.io;

import com.rainerhahnekamp.sneakythrow.functional.SneakyConsumer;
import com.rainerhahnekamp.sneakythrow.functional.SneakyFunction;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.FileVisitOption;
import java.nio.file.Path;
import java.util.List;
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
     * Using fileSystemUtils::isRegularFile as pathsFilter!
     */
    public static SimpleDirectory fixedRootPath(Path path) {
        return regularFiles(() -> path);
    }

    public static SimpleDirectory regularFiles(Supplier<Path> rootPathSupplier) {
        FileSystemUtils fileSystemUtils = new FileSystemUtils();
        return of(fileSystemUtils, rootPathSupplier, fileSystemUtils::isRegularFile);
    }

    public static SimpleDirectory of(FileSystemUtils fsUtils,
                                     Supplier<Path> rootPathSupplier, Predicate<Path> pathsFilter) {
        return new SimpleDirectory(FOLLOW_LINKS, fsUtils, rootPathSupplier, pathsFilter);
    }

    public <R, E extends Exception> R transformPathStream(
            SneakyFunction<Stream<Path>, R, E>
                    pathsStreamProcessor) throws IOException, E {
        return transformPathStream(rootPathSupplier.get(), pathsStreamProcessor);
    }

    public <R, E extends Exception> R transformPathStream(
            Path start, SneakyFunction<Stream<Path>, R, E>
            pathsStreamProcessor) throws IOException, E {
        try (Stream<Path> paths = fsUtils.walk(resolvePath(start), fileVisitOption)) {
            return pathsStreamProcessor.apply(paths.filter(pathsFilter));
        }
    }

    public <E extends Exception> void doWithPathStream(
            SneakyConsumer<Stream<Path>, E>
                    pathsStreamConsumer) throws IOException, E {
        doWithPathStream(rootPathSupplier.get(), pathsStreamConsumer);
    }

    public <E extends Exception> void doWithPathStream(Path start,
                                                       SneakyConsumer<Stream<Path>, E>
                                                               pathsStreamConsumer) throws IOException, E {
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
        return transformPathStream(start, Stream::toList);
    }

    public Path getRoot() {
        return rootPathSupplier.get();
    }
}
