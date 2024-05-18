package ro.go.adrhc.util.fn;

import com.rainerhahnekamp.sneakythrow.functional.SneakySupplier;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class SneakySupplierUtils {
	public static <T, E extends Exception> List<T>
	failToEmptyList(SneakySupplier<List<T>, E> sneakySupplier) {
		try {
			return sneakySupplier.get();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return List.of();
	}
}
