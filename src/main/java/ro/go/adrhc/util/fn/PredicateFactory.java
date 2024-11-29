package ro.go.adrhc.util.fn;

import com.rainerhahnekamp.sneakythrow.functional.SneakyPredicate;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.function.Predicate;

@UtilityClass
@Slf4j
public class PredicateFactory {
	public static <T> Predicate<T> failToFalse(SneakyPredicate<T, IOException> predicate) {
		return t -> {
			try {
				return predicate.test(t);
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
			return false;
		};
	}

	@SafeVarargs
	public static <T> Predicate<? super T> anyMatch(Predicate<? super T>... predicate) {
		return t -> Arrays.stream(predicate).anyMatch(p -> p.test(t));
	}

	@SafeVarargs
	public static <T> Predicate<? super T> noneMatch(Predicate<? super T>... predicate) {
		return t -> Arrays.stream(predicate).noneMatch(p -> p.test(t));
	}

	@SafeVarargs
	public static <T> Predicate<? super T> allMatch(Predicate<? super T>... predicate) {
		return t -> Arrays.stream(predicate).allMatch(p -> p.test(t));
	}
}
