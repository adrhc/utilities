package ro.go.adrhc.util;

import com.rainerhahnekamp.sneakythrow.functional.SneakySupplier;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.function.Supplier;

import static ro.go.adrhc.util.Slf4jUtils.logError;

@UtilityClass
@Slf4j
public class OptionalUtils {
	public <T> Optional<T> ofNullable(Supplier<? extends T> supplier) {
		return Optional.ofNullable(supplier.get());
	}

	public <T, E extends Exception> Optional<T>
	ofSneaky(SneakySupplier<? extends T, E> supplier) {
		try {
			return Optional.of(supplier.get());
		} catch (Exception e) {
			logError(log, e);
		}
		return Optional.empty();
	}
}
