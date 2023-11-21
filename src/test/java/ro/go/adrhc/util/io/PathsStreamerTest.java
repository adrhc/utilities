package ro.go.adrhc.util.io;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.go.adrhc.util.concurrency.streamer.PathsStreamer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@Slf4j
class PathsStreamerTest {
	@Test
	void create(@TempDir Path tmp) throws IOException {
		PathsStreamer pathsStreamCreator = PathsStreamer.create(
				Executors.newSingleThreadExecutor(),
				SimpleDirectory.fixedRootPath(tmp));

		Files.createFile(tmp.resolve("test-file.txt"));

		assertThat(pathsStreamCreator.toStream()
				.map(Path::getFileName).map(Path::toString))
				.containsOnly("test-file.txt");
	}
}