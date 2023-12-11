package ro.go.adrhc.util.collection;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * setOf(collection).map(converter)
 * setOf(collection).flatMap(converter)
 * setOf(collection).optionalFlatMap(converter)
 */
public record SetDsl<T>(Collection<T> collection) {
    /**
     * The Set is constructed after conversion.
     */
    public static <T> SetDsl<T> setOf(Collection<T> collection) {
        return new SetDsl<>(collection);
    }

    /**
     * Nulls are discarded!
     */
    public <R> Set<R> map(Function<? super T, R> converter) {
        return collection.stream().map(converter).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    public <R> Set<R> flatMap(Function<? super T, Optional<R>> converter) {
        return collection.stream().map(converter).flatMap(Optional::stream).collect(Collectors.toSet());
    }

    public <R> Set<R> optionalFlatMap(Function<? super T, Optional<R>> converter) {
        return flatMap(converter);
    }
}
