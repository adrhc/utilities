package ro.go.adrhc.util.optional;

import com.rainerhahnekamp.sneakythrow.functional.SneakySupplier;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.function.Supplier;

@UtilityClass
@Slf4j
public class OptionalFactory {
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
