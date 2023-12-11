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
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@Slf4j
class FilesMetadataLoaderTest {
    @Test
    void loadAll(@TempDir Path tmp) throws IOException {
        FilesMetadataLoader<String> metadataLoader = FilesMetadataLoader.create(
                Executors.newSingleThreadExecutor(), Executors.newCachedThreadPool(),
                SimpleDirectory.fixedRootPath(tmp), p -> p.getFileName().toString());

        Files.createFile(tmp.resolve("test-file1.txt"));
        Files.createFile(tmp.resolve("test-file2.txt"));

        Stream<String> strings = metadataLoader.loadAll();
        assertThat(strings).containsOnly("test-file1.txt", "test-file2.txt");
    }
}