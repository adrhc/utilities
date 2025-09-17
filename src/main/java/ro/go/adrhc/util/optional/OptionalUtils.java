package ro.go.adrhc.util.optional;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.function.Consumer;

@UtilityClass
@Slf4j
public class OptionalUtils {
	/**
	 * @param consumer receives the optional's value, if present
	 * @return optional
	 */
	public static <T> Optional<T> ifPresent(Optional<T> optional, Consumer<? super T> consumer) {
		optional.ifPresent(consumer);
		return optional;
	}

	/**
	 * @param consumer receives the optional's value, if present
	 * @return optional.isPresent()
	 */
	public static <T> boolean ifPresentTrue(Optional<T> optional, Consumer<T> consumer) {
		optional.ifPresent(consumer);
		return optional.isPresent();
	}

	/**
	 * @param consumer   receives the optional's value, if present
	 * @param missAction is run, if optional is empty
	 * @return optional
	 */
	public static <T> Optional<T> ifPresentOrElse(
		Optional<T> optional, Consumer<? super T> consumer, Runnable missAction) {
		optional.ifPresentOrElse(consumer, missAction);
		return optional;
	}

	/**
	 * @param missAction is run, if optional is empty
	 * @return optional
	 */
	public static <T> Optional<T> ifMissing(Optional<T> optional, Runnable missAction) {
		if (optional.isEmpty()) {
			missAction.run();
		}
		return optional;
	}
}
