package ro.go.adrhc.util.fn;

import com.rainerhahnekamp.sneakythrow.functional.SneakySupplier;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@UtilityClass
@Slf4j
public class SneakySupplierUtils {
	public static <T> T failToNull(SneakySupplier<T, ?> supplier) {
		try {
			return supplier.get();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	public static <T> List<T> failToEmptyList(SneakySupplier<List<T>, ?> sneakySupplier) {
		try {
			return sneakySupplier.get();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return List.of();
	}
}
