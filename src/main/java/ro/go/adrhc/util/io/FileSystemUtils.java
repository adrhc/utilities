package ro.go.adrhc.util.io;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.FileAttribute;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
public class FileSystemUtils {
	public void cleanDirectory(Path path) throws IOException {
		FileUtils.cleanDirectory(path.toFile());
	}

	/**
	 * Create the parent directory chain too!
	 */
	public void writeUtf8String(Path path, String utf8Text) throws IOException {
		createParentDirectories(path);
		Files.writeString(path, utf8Text, UTF_8);
	}

	/**
	 * Create the parent directory chain too!
	 */
	public void createFile(Path path, FileAttribute<?>... attrs) throws IOException {
		createParentDirectories(path);
		Files.createFile(path, attrs);
	}

	public long fileCount(Predicate<Path> pathPredicate, Path directory) throws IOException {
		try (Stream<Path> paths = Files.list(directory)) {
			return paths.filter(pathPredicate).count();
		}
	}

	public String readString(Path path) throws IOException {
		return Files.readString(path);
	}

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

	public long size(Path path) throws IOException {
		return Files.size(path);
	}

	public long size(Path path, long sizeIfIOException) {
		try {
			return Files.size(path);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			return sizeIfIOException;
		}
	}

	public boolean isRegularFile(Path path, LinkOption... options) {
		return Files.isRegularFile(path, options);
	}

	private void createParentDirectories(Path target) throws IOException {
		Path parent = target.getParent();
		if (parent != null) {
			Files.createDirectories(parent);
		}
	}
}
