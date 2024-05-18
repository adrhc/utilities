package ro.go.adrhc.util.concurrency.streamer;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.go.adrhc.util.io.SimpleDirectory;
import ro.go.adrhc.util.streamer.PathsStreamer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@Slf4j
class PathsStreamerTest {
	@Test
	void toStream(@TempDir Path tmp) throws IOException {
		PathsStreamer pathsStreamer = pathsStreamer(tmp);
		initFS(tmp);
		assertThat(pathsStreamer.toStream()
				.map(Path::getFileName).map(Path::toString))
				.containsOnly("test-file1.txt", "test-file2.txt", "test-file3.txt");
	}

	@Disabled
	@Test
	void streamClosure() {
		Path userHome = FileUtils.getUserDirectory().toPath();
		PathsStreamer pathsStreamer = pathsStreamer(userHome);
		Optional<Path> optional;
		try (Stream<Path> stream = pathsStreamer.toStream()) {
			optional = stream.findFirst();
		}
		assertThat(optional).isPresent();
	}

	private static void initFS(Path tmp) throws IOException {
		Files.createFile(tmp.resolve("test-file1.txt"));
		Files.createFile(tmp.resolve("test-file2.txt"));
		Files.createFile(tmp.resolve("test-file3.txt"));
	}

	private static PathsStreamer pathsStreamer(Path tmp) {
		return PathsStreamer.create(
				Executors.newSingleThreadExecutor(),
				SimpleDirectory.fixedRootPath(tmp));
	}
}