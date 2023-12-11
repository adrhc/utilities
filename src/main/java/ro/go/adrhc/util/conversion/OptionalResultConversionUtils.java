package ro.go.adrhc.util.conversion;

import com.rainerhahnekamp.sneakythrow.functional.SneakyFunction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import static ro.go.adrhc.util.collection.StreamUtils.stream;

public class OptionalResultConversionUtils {
    public static <T, R, E extends Exception> List<R> convertAllSneaky(
            SneakyFunction<T, Optional<R>, E> converter, Collection<T> items) throws E {
        List<R> result = new ArrayList<>();
        for (T t : items) {
            converter.apply(t).ifPresent(result::add);
        }
        return result;
    }

    public static <T, R> Stream<R> convertStream(Function<T, Optional<R>> converter, Stream<T> tStream) {
        return tStream.map(converter).flatMap(Optional::stream);
    }

    public static <T, R> List<R> convertCollection(Function<T, Optional<R>> converter, Collection<T> tCollection) {
        return tCollection.stream().map(converter).flatMap(Optional::stream).toList();
    }

    public static <T, R> List<R> convertIterable(Function<T, Optional<R>> converter, Iterable<T> tIterable) {
        return stream(tIterable).map(converter).flatMap(Optional::stream).toList();
    }
}
