package ro.go.adrhc.util.fn;

import com.rainerhahnekamp.sneakythrow.functional.SneakyConsumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class RunnableUtils {
	public static <T, E extends Exception> void run(SneakyConsumer<T, E> sneakyConsumer, T t) {
		try {
			sneakyConsumer.accept(t);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
