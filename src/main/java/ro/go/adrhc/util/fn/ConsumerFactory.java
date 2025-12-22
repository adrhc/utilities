package ro.go.adrhc.util.fn;

import com.rainerhahnekamp.sneakythrow.functional.SneakyBiConsumer;
import com.rainerhahnekamp.sneakythrow.functional.SneakyFunction;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@UtilityClass
@Slf4j
public class ConsumerFactory {
	/**
	 * @return a Consumer that should be invoked using sneakyFn's parameter
	 */
	public static <T, E extends Exception> Consumer<T>
	toSilentConsumer(SneakyFunction<T, ?, E> sneakyFn) {
		return t -> {
			try {
				sneakyFn.apply(t);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		};
	}

	/**
	 * @param p1 is the sneakyBiConsumer's 1st parameter
	 * @return a Consumer that should be invoked using sneakyBiConsumer's 2nd parameter
	 */
	public static <P1, P2, E extends Exception> Consumer<P2>
	toSilentP2Consumer(SneakyBiConsumer<P1, P2, E> sneakyBiConsumer, P1 p1) {
		return p2 -> {
			try {
				sneakyBiConsumer.accept(p1, p2);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		};
	}

	/**
	 * @param p2 is the biConsumer's 2st parameter
	 * @return a Consumer that should be invoked using biConsumer's 1nd parameter
	 */
	public static <P1, P2> Consumer<P1>
	toP1Consumer(BiConsumer<P1, P2> biConsumer, P2 p2) {
		return p1 -> biConsumer.accept(p1, p2);
	}

	/**
	 * @param p1 is the biConsumer's 1st parameter
	 * @return a Consumer that should be invoked using biConsumer's 2nd parameter
	 */
	public static <P1, P2> Consumer<P2>
	toP2Consumer(BiConsumer<P1, P2> biConsumer, P1 p1) {
		return p2 -> biConsumer.accept(p1, p2);
	}

	/**
	 * @param p1 is the triConsumer's 1st parameter
	 * @param p2 is the triConsumer's 2nd parameter
	 * @return a Consumer that should be invoked using triConsumer's 3rd parameter
	 */
	public static <P1, P2, P3> Consumer<P3>
	toP3Consumer(TriConsumer<P1, P2, P3> triConsumer, P1 p1, P2 p2) {
		return p3 -> triConsumer.accept(p1, p2, p3);
	}
}
