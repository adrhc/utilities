package ro.go.adrhc.util.io;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.util.EnumSet;
import java.util.Optional;

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
