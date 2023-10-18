package ro.go.adrhc.util.collection;

import lombok.RequiredArgsConstructor;
import ro.go.adrhc.util.pair.Pair;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

/**
 * join(collectionOrStream1, collection2).combinedBy(combiner).on(joiningRule)
 */
public record JoinDsl<T, U>(Stream<T> first, Collection<U> second) {
	public static <T, U> JoinDsl<T, U> join(Collection<T> first, Collection<U> second) {
		return new JoinDsl<>(first.stream(), second);
	}

	public static <T, U> JoinDsl<T, U> join(Stream<T> first, Collection<U> second) {
		return new JoinDsl<>(first, second);
	}

	public Stream<Pair<T, U>> on(BiPredicate<T, U> joiningRule) {
		return on(joiningRule, Pair::new);
	}

	public <R> CombinedBy<R> combinedBy(BiFunction<T, U, R> combiner) {
		return new CombinedBy<>(combiner);
	}

	private <R> Stream<R> on(BiPredicate<T, U> joiningRule, BiFunction<T, U, R> combiner) {
		return first
				.flatMap(it1 -> second.stream()
						.filter(it2 -> joiningRule.test(it1, it2))
						.map(it2 -> combiner.apply(it1, it2)));
	}

	@RequiredArgsConstructor
	public class CombinedBy<R> {
		public final BiFunction<T, U, R> combiner;

		public Stream<R> on(BiPredicate<T, U> joiningRule) {
			return JoinDsl.this.on(joiningRule, combiner);
		}
	}
}
