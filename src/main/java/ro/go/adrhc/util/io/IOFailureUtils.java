package ro.go.adrhc.util.io;

import com.rainerhahnekamp.sneakythrow.functional.SneakyFunction;
import com.rainerhahnekamp.sneakythrow.functional.SneakyPredicate;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

@Slf4j
public class IOFailureUtils {
	public static <T> Consumer<T> toSafeConsumer(SneakyFunction<T, ?, IOException> function) {
		return t -> {
			try {
				function.apply(t);
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		};
	}

	public static <T, R> Function<T, R> failToNull(SneakyFunction<T, R, IOException> function) {
		return t -> {
			try {
				return function.apply(t);
			} catch (IOException e) {
				log.error(e.getMessage(), e);
				return null;
			}
		};
	}

	public static <T> Predicate<T> failToFalse(SneakyPredicate<T, IOException> predicate) {
		return t -> {
			try {
				return predicate.test(t);
			} catch (IOException e) {
				log.error(e.getMessage(), e);
				return false;
			}
		};
	}
}
