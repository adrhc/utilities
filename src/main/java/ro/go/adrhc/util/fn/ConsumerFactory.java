package ro.go.adrhc.util.fn;

import com.rainerhahnekamp.sneakythrow.functional.SneakyBiConsumer;
import com.rainerhahnekamp.sneakythrow.functional.SneakyConsumer;
import com.rainerhahnekamp.sneakythrow.functional.SneakyFunction;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@UtilityClass
@Slf4j
public class ConsumerFactory {
	public static <T, E extends Exception> Consumer<T> silence(SneakyFunction<T, ?, E> sneakyFn) {
		return t -> {
			try {
				sneakyFn.apply(t);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		};
	}

	public static <P1, P2, E extends Exception> Consumer<P2>
	toP2Consumer(SneakyBiConsumer<P1, P2, E> sneakyBiConsumer, P1 p1) {
		return p2 -> {
			try {
				sneakyBiConsumer.accept(p1, p2);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		};
	}

	public static <T, E extends Exception> Consumer<T> silence(SneakyConsumer<T, E> sneakyConsumer) {
		return t -> {
			try {
				sneakyConsumer.accept(t);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		};
	}
}
