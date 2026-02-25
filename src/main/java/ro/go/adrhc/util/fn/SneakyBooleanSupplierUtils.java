package ro.go.adrhc.util.fn;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class SneakyBooleanSupplierUtils {
	public static boolean failToFalse(SneakyBooleanSupplier<?> sneakyBooleanSupplier) {
		try {
			return sneakyBooleanSupplier.get();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return false;
	}

}
