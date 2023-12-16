package ro.go.adrhc.util.collection;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static ro.go.adrhc.util.stream.StreamUtils.stream;

@ExtendWith(MockitoExtension.class)
@Slf4j
class StreamUtilsTest {
    @Test
    void streamTest() {
        Optional<String> optional = stream(List.of("value1", "value2")).findFirst();
        assertThat(optional).hasValue("value1");
    }
}