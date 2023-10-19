package ro.go.adrhc.util.io;

import lombok.RequiredArgsConstructor;

import java.nio.file.Path;
import java.util.Set;

import static java.nio.file.Files.isRegularFile;
import static org.apache.commons.io.FilenameUtils.getExtension;
import static ro.go.adrhc.util.io.PathUtils.hasFilename;

@RequiredArgsConstructor
public class SupportedExtensions {
	private final Set<String> supportedExtensions;

	public boolean supports(Path path) {
		return isRegularFile(path) && hasFilename(path) && isSupportedFile(path);
	}

	private boolean isSupportedFile(Path path) {
		return supportedExtensions.contains(
				getExtension(path.getFileName().toString()).toLowerCase());
	}
}
