package ro.go.adrhc.util.io;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import ro.go.adrhc.util.Assert;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.nio.file.attribute.PosixFilePermission.*;

/**
 * Created by IntelliJ IDEA.
 * User: adrhc
 * Date: Mar 4, 2011
 * Time: 10:39:03 AM
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
public class PathUtils {
	public static final EnumSet<PosixFilePermission> RWX =
			EnumSet.of(OWNER_EXECUTE, OWNER_READ, OWNER_WRITE);

	public static Path removeFileExtension(Path path) {
		// Get the file name as a String
		String fileName = path.getFileName().toString();

		// Find the position of the last dot
		int dotIndex = fileName.lastIndexOf('.');

		return (dotIndex == -1)
				? path // No extension, do nothing
				: path.resolveSibling(fileName.substring(0, dotIndex));
	}

	public static Path replaceFileExtension(String newExtension, Path path) {
		// Get the file name as a String
		String fileName = path.getFileName().toString();

		// Find the position of the last dot
		int dotIndex = fileName.lastIndexOf('.');

		// Construct the new file name with the replacement extension
		String newFileName = (dotIndex == -1)
				? fileName // No extension, do nothing
				: fileName.substring(0, dotIndex) + "." + newExtension;

		// Return the updated Path with the new file name
		return path.resolveSibling(newFileName);
	}

	public static Path switchRoot(Path target, Path source, Path file) {
		Path relativePath = source.relativize(file);
		return target.resolve(relativePath);
	}

	/**
	 * @return path if referencePath is null otherwise resolve path against referencePath
	 */
	public static Path resolve(Path referencePath, Path path) {
		return referencePath == null ? path : referencePath.resolve(path);
	}

	/**
	 * @return path if is an absolute path otherwise relativize path against referencePath
	 */
	public static Path relativize(Path referencePath, Path path) {
		return path.isAbsolute() ? referencePath.relativize(path) : path;
	}

	public static Set<Path> relativize(Path referencePath, Collection<Path> paths) {
		Assert.isTrue(referencePath.isAbsolute(), "referencePath must be absolute!");
		Assert.isTrue(paths.stream().allMatch(p -> p.startsWith(referencePath)),
				"All paths must have referencePath as parent!");
		return paths.stream().map(referencePath::relativize).collect(Collectors.toSet());
	}

	/**
	 * here .local is considered a file without a name but with only extension
	 */
	public static boolean hasFilename(Path path) {
		return !path.getFileName().toString().trim().startsWith(".");
	}

	public static boolean isPathEndingWith(String filenameEnding, Path path) {
		return FilenameUtils.getBaseName(path.toString()).endsWith(filenameEnding);
	}

	public static boolean isNullOrRelative(Path path) {
		return path == null || !path.isAbsolute();
	}

	public static Optional<Path> setPosixFilePermissions(
			EnumSet<PosixFilePermission> permissions, Path path) {
		try {
			return Optional.of(Files.setPosixFilePermissions(path, permissions));
		} catch (Exception e) {
			log.error("\nCan't set permissions on {}!", path);
		}
		return Optional.empty();
	}

	public static Optional<Path> parentOf(Path path) {
		return path == null ? Optional.empty() : Optional.of(path.getParent());
	}
}
