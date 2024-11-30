package ro.go.adrhc.util.io;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.nio.file.Path;
import java.util.Set;

import static java.nio.file.Files.isRegularFile;
import static org.apache.commons.io.FilenameUtils.getExtension;
import static ro.go.adrhc.util.io.PathUtils.hasFilename;

@RequiredArgsConstructor
@ToString
public class SupportedExtensions {
	private final Set<String> supportedExtensions;
	private final boolean caseSensitive;

	public static SupportedExtensions ofCaseInsensitive(String... extension) {
		return new SupportedExtensions(Set.of(extension), false);
	}

	public static SupportedExtensions ofCaseSensitive(String... extension) {
		return new SupportedExtensions(Set.of(extension), true);
	}

	public boolean supports(Path path) {
		return isRegularFile(path) && hasFilename(path) && isSupportedFile(path);
	}

	private boolean isSupportedFile(Path path) {
		return supportedExtensions.contains(
				getExtension(path.getFileName().toString()).toLowerCase());
	}
}
