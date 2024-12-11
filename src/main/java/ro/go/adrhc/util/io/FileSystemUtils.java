package ro.go.adrhc.util.io;

import com.rainerhahnekamp.sneakythrow.functional.SneakyConsumer;
import com.rainerhahnekamp.sneakythrow.functional.SneakyFunction;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.FileAttribute;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static ro.go.adrhc.util.io.PathUtils.swapRoot;

@Slf4j
public class FileSystemUtils {
	public <T> T readThroughTmp(Path targetRoot, Path tmpRoot, Path filePath,
			SneakyFunction<Path, T, IOException> reader) throws IOException {
		Path tmpFilePath = swapRoot(tmpRoot, targetRoot, filePath);
		if (exists(tmpFilePath)) {
			return reader.apply(tmpFilePath);
		} else {
			return reader.apply(filePath);
		}
	}

	public Path writeThroughTmp(Path targetRoot, Path tmpRoot, Path filePath,
			SneakyConsumer<Path, IOException> writer) throws IOException {
		Path tmpFilePath = swapRoot(tmpRoot, targetRoot, filePath);
		createParentDirectories(tmpFilePath);
		writer.accept(tmpFilePath);
		return move(tmpFilePath, targetRoot, REPLACE_EXISTING);
	}

	public boolean isReadable(Path path) {
		return Files.isReadable(path);
	}

	public List<String> readLines(Path path) throws IOException {
		return Files.readAllLines(path, StandardCharsets.UTF_8);
	}

	public void cleanDirectory(Path dir) throws IOException {
		if (this.exists(dir)) {
			FileUtils.cleanDirectory(dir.toFile());
		} else {
			log.warn("\n{} can't be cleaned because is missing!", dir);
		}
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

	/**
	 * This method must be used within a try-with-resources statement or similar control structure to ensure that the stream's open directory is closed promptly after the stream's operations have completed.
	 */
	public Stream<Path> list(Path start) throws IOException {
		return Files.list(start);
	}

	/**
	 * This method must be used within a try-with-resources statement or similar control structure to ensure that the stream's open directory is closed promptly after the stream's operations have completed.
	 */
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

	public long sizeOrDefault(Path path, long sizeIfIOException) {
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

	public Path createDirectories(Path dir, FileAttribute<?>... attrs) throws IOException {
		return Files.createDirectories(dir, attrs);
	}

	private Path createParentDirectories(Path target, FileAttribute<?>... attrs) throws IOException {
		Path parent = target.getParent();
		return parent != null ? createDirectories(parent, attrs) : null;
	}
}
