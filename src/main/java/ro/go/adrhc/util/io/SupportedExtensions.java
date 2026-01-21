package ro.go.adrhc.util.io;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

import static java.nio.file.Files.isRegularFile;
import static org.apache.commons.io.FilenameUtils.getExtension;
import static ro.go.adrhc.util.io.PathUtils.hasFilename;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class SupportedExtensions {
	private final Set<String> supportedExtensions;

	public static SupportedExtensions of(String... extension) {
		return of(Set.of(extension));
	}

	public static SupportedExtensions of(Set<String> extensions) {
		return new SupportedExtensions(extensions.stream()
			.map(String::toLowerCase).collect(Collectors.toSet()));
	}

	/**
	 * Uses case-insensitive file extension comparison!
	 */
	public boolean supports(Path path) {
		return isRegularFile(path) && hasFilename(path) && isSupportedFile(path);
	}

	private boolean isSupportedFile(Path path) {
		return supportedExtensions.contains(
			getExtension(path.getFileName().toString()).toLowerCase());
	}
}
