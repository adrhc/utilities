package ro.go.adrhc.util;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

@FunctionalInterface
public interface Breakable<T> {
	boolean isBroken();

	default boolean isOk() {
		return !isBroken();
	}

	/**
	 * @return empty if broken
	 */
	default Optional<T> toOptional() {
		return isBroken() ? Optional.empty() : Optional.of((T) this);
	}

	/**
	 * @return empty if broken
	 */
	default <R> Optional<R> map(Function<? super T, R> notBrokenMapper) {
		return isBroken() ? Optional.empty() : Optional.ofNullable(notBrokenMapper.apply((T) this));
	}

	/**
	 * @return resultForBrokenInput if broken
	 */
	default <R> Optional<R> map(R resultForBrokenInput, Function<? super T, R> notBrokenMapper) {
		return Optional.ofNullable(isBroken() ? resultForBrokenInput : notBrokenMapper.apply((T) this));
	}

	default <R> Optional<R> map(
			Function<? super T, R> brokenMapper,
			Function<? super T, R> notBrokenMapper) {
		Function<? super T, R> mapper = isBroken() ? brokenMapper : notBrokenMapper;
		return Optional.ofNullable(mapper.apply((T) this));
	}

	/**
	 * @return empty if broken
	 */
	default Stream<T> toStream() {
		return isBroken() ? Stream.empty() : Stream.of((T) this);
	}
}
