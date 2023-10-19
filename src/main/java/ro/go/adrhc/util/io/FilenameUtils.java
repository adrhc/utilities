package ro.go.adrhc.util.io;

import java.nio.file.Path;
import java.util.Optional;

import static ro.go.adrhc.util.text.StringUtils.hasText;

public class FilenameUtils {
	public static String addSuffix(String filename, String suffix) {
		int extensionDot = filename.lastIndexOf('.');
		return filename.substring(0, extensionDot)
				+ suffix + filename.substring(extensionDot);
	}

	public static Optional<Path> filenameToPath(String rawFilename) {
		String filename = org.apache.commons.io.FilenameUtils.getName(rawFilename);
		return hasText(filename) ? Optional.of(Path.of(filename)) : Optional.empty();
	}
}
