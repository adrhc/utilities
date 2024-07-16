package ro.go.adrhc.util.specialcase;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

@FunctionalInterface
public interface Broken<T> {
	boolean isBroken();

	default boolean isOk() {
		return !isBroken();
	}

	default <R> Optional<R> mapIfOk(Function<? super T, R> mapper) {
		return isBroken() ? Optional.empty() : Optional.ofNullable(mapper.apply((T) this));
	}

	default Optional<T> toOptional() {
		return isBroken() ? Optional.empty() : Optional.of((T) this);
	}

	default Stream<T> toStream() {
		return isBroken() ? Stream.empty() : Stream.of((T) this);
	}
}
