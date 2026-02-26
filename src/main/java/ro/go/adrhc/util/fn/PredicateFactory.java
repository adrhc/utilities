package ro.go.adrhc.util.fn;

import com.rainerhahnekamp.sneakythrow.functional.SneakyPredicate;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.function.Predicate;

@UtilityClass
@Slf4j
public class PredicateFactory {
	public static <T> Predicate<T> toPredicate(SneakyPredicate<? super T, ?> predicate) {
		return t -> {
			try {
				return predicate.test(t);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			return false;
		};
	}

	@SafeVarargs
	public static <T> Predicate<T> anyMatch(Predicate<? super T>... predicate) {
		return t -> Arrays.stream(predicate).anyMatch(p -> p.test(t));
	}

	@SafeVarargs
	public static <T> Predicate<T> noneMatch(Predicate<? super T>... predicate) {
		return t -> Arrays.stream(predicate).noneMatch(p -> p.test(t));
	}

	@SafeVarargs
	public static <T> Predicate<T> allMatch(Predicate<? super T>... predicate) {
		return t -> Arrays.stream(predicate).allMatch(p -> p.test(t));
	}
}
