package ro.go.adrhc.util.collection;

import lombok.experimental.UtilityClass;

import java.util.stream.Stream;

@UtilityClass
public class IterableUtils {
    public static <T> Iterable<T> iterable(Stream<T> stream) {
        return stream::iterator;
    }
}
