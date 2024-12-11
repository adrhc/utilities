package ro.go.adrhc.util.io;

import com.rainerhahnekamp.sneakythrow.functional.SneakyConsumer;
import com.rainerhahnekamp.sneakythrow.functional.SneakyFunction;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.Path;

@RequiredArgsConstructor
public class PassThroughFSCache {
	private final FileSystemUtils fsUtils;
	private final Path tmpRoot;
	private final Path targetRoot;

	public <T> T read(Path filePath,
			SneakyFunction<Path, T, IOException> reader) throws IOException {
		return fsUtils.readThroughTmp(targetRoot, tmpRoot, filePath, reader);
	}

	public Path write(Path filePath,
			SneakyConsumer<Path, IOException> writer) throws IOException {
		return fsUtils.writeThroughTmp(targetRoot, tmpRoot, filePath, writer);
	}
}
