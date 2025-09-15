package ro.go.adrhc.util.optional;

import com.rainerhahnekamp.sneakythrow.functional.SneakySupplier;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

@UtilityClass
@Slf4j
public class OptionalUtils {
	/**
	 * Consume if present then return true, otherwise return false.
	 */
	public static <T> boolean ifPresentTrue(Optional<T> optional, Consumer<T> consumer) {
		optional.ifPresent(consumer);
		return optional.isPresent();
	}

	/**
	 * Handle a miss then return "optional".
	 */
	public static <T> Optional<T> ifMissing(Optional<T> optional, Runnable missAction) {
		if (optional.isEmpty()) {
			missAction.run();
		}
		return optional;
	}

	/**
	 * Consume if present then return "optional".
	 */
	public static <T> Optional<T> ifPresent(Optional<T> optional, Consumer<? super T> consumer) {
		optional.ifPresent(consumer);
		return optional;
	}

	public static <T> Optional<T> ifPresentOrElse(
		Optional<T> optional, Consumer<? super T> consumer, Runnable otherwise) {
		optional.ifPresentOrElse(consumer, otherwise);
		return optional;
	}

	/**
	 * @return a not empty Optional of the "supplier"'s product
	 */
	public <T> Optional<T> of(Supplier<? extends T> supplier) {
		return Optional.of(supplier.get());
	}

	/**
	 * @return a possibly empty Optional of the "supplier"'s product
	 */
	public <T> Optional<T> ofNullable(Supplier<? extends T> supplier) {
		return Optional.ofNullable(supplier.get());
	}

	/**
	 * @return a not empty Optional of the "supplier"'s product or empty on error
	 */
	public <T, E extends Exception> Optional<T>
	ofSneaky(SneakySupplier<? extends T, E> supplier) {
		try {
			return Optional.of(supplier.get());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return Optional.empty();
	}
}
