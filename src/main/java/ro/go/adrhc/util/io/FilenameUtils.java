package ro.go.adrhc.util.io;

import lombok.NonNull;
import org.apache.commons.lang3.RegExUtils;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.Optional;

import static ro.go.adrhc.util.text.StringUtils.hasText;

public class FilenameUtils {
	public static final Comparator<Path> CASE_IGNORE_FILENAME_COMPARATOR =
		Comparator.comparing(o -> o.getFileName().toString(), String::compareToIgnoreCase);

	/**
	 * see https://stackoverflow.com/questions/1976007/what-characters-are-forbidden-in-windows-and-linux-directory-names#:~:text=Under%20Linux%20and%20other%20Unix,'%5C0'%20and%20slash%20'%2F'%20.
	 * <p>
	 * The forbidden printable ASCII characters are:
	 * <p>
	 * Linux/Unix:
	 * / (forward slash)
	 * Windows:
	 * < (less than)
	 * > (greater than)
	 * : (colon - sometimes works, but is actually NTFS Alternate Data Streams)
	 * " (double quote)
	 * / (forward slash)
	 * \ (backslash)
	 * | (vertical bar or pipe)
	 * ? (question mark)
	 * * (asterisk)
	 */
	@NonNull
	public static String sanitize(String fileName) {
		return RegExUtils
			.replacePattern(fileName, "[<>:\"/\\\\|\\?\\*!;\\{\\}]", " ")
			.replaceAll("\\s+", " ")
			.trim();
	}

	public static Optional<String> filenameNoExt(Path path) {
		return Optional.ofNullable(path)
			.map(Path::getFileName)
			.map(Path::toString)
			.map(org.apache.commons.io.FilenameUtils::removeExtension);
	}

	public static String addSuffix(String filename, String suffix) {
		int extensionDot = filename.lastIndexOf('.');
		return filename.substring(0, extensionDot)
		       + suffix + filename.substring(extensionDot);
	}

	public static String removeSuffix(String filename, String suffix) {
		int lastIndex = filename.lastIndexOf(suffix);
		if (lastIndex < 0) {
			return filename;
		} else {
			return filename.substring(0, lastIndex) +
			       filename.substring(lastIndex + suffix.length());
		}
	}

	public static Optional<Path> filenameToPath(String rawFilename) {
		String filename = org.apache.commons.io.FilenameUtils.getName(rawFilename);
		return hasText(filename) ? Optional.of(Path.of(filename)) : Optional.empty();
	}
}
