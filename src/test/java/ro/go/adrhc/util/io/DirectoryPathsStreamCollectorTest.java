package ro.go.adrhc.util.io;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@Slf4j
class DirectoryPathsStreamCollectorTest {
	@Test
	void create(@TempDir Path tmp) throws IOException {
		FileSystemUtils fileSystemUtils = new FileSystemUtils();
		DirectoryPathsStreamCollector pathsStreamCreator = new DirectoryPathsStreamCollector(
				Executors.newSingleThreadExecutor(),
				SimpleDirectory.of(fileSystemUtils, () -> tmp, fileSystemUtils::isRegularFile));

		Files.createFile(tmp.resolve("test-file.txt"));

		assertThat(pathsStreamCreator.create()
				.map(Path::getFileName).map(Path::toString))
				.containsOnly("test-file.txt");
	}
}