package ro.go.adrhc.util.fn;

import com.rainerhahnekamp.sneakythrow.functional.SneakyConsumer;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class SneakyConsumerUtils {
	public static <T, E extends Exception> void
	runSilently(SneakyConsumer<T, E> sneakyConsumer, T t) {
		try {
			sneakyConsumer.accept(t);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
