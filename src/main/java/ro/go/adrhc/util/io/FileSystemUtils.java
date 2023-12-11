package ro.go.adrhc.util.io;

import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;

public class FileSystemUtils {
    public Stream<Path> walk(Path start, FileVisitOption... options) throws IOException {
        return Files.walk(start, options);
    }

    public Path copy(Path source, Path target, CopyOption... options) throws IOException {
        createParentDirectories(target);
        return Files.copy(source, target, options);
    }

    public Path move(Path source, Path target, CopyOption... options) throws IOException {
        createParentDirectories(target);
        return Files.move(source, target, options);
    }

    public boolean deleteIfExists(Path path) throws IOException {
        return Files.deleteIfExists(path);
    }

    public boolean exists(Path path, LinkOption... options) {
        return Files.exists(path, options);
    }

    public boolean isRegularFile(Path path, LinkOption... options) {
        return Files.isRegularFile(path, options);
    }

    private void createParentDirectories(Path target) throws IOException {
        Path parent = target.getParent();
        if (parent != null && !Files.exists(parent)) {
            Files.createDirectories(parent);
        }
    }
}
