package ro.go.adrhc.util.fn;

import java.util.Arrays;
import java.util.function.Predicate;

public class PredicateUtils {
	public static <T> boolean areSameInstance(T t1, T t2) {
		return t1 == t2;
	}

	public static <V> boolean alwaysFalse(V v) {
		return false;
	}

	public static <V> boolean alwaysTrue(V v) {
		return true;
	}

	@SafeVarargs
	public static <T> Predicate<T> noneMatch(Predicate<T>... predicate) {
		return t -> Arrays.stream(predicate).noneMatch(p -> p.test(t));
	}

	@SafeVarargs
	public static <T> Predicate<T> allMatch(Predicate<T>... predicate) {
		return t -> Arrays.stream(predicate).allMatch(p -> p.test(t));
	}
}
