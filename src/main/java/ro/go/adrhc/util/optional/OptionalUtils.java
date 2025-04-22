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
	public static <T> Optional<T> ifPresent(Consumer<T> consumer, Optional<T> optional) {
		optional.ifPresent(consumer);
		return optional;
	}

	public <T> Optional<T> of(Supplier<? extends T> supplier) {
		return Optional.of(supplier.get());
	}

	public <T> Optional<T> ofNullable(Supplier<? extends T> supplier) {
		return Optional.ofNullable(supplier.get());
	}

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
