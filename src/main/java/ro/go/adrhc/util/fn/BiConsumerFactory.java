package ro.go.adrhc.util.fn;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@UtilityClass
@Slf4j
public class BiConsumerFactory {
	/**
	 * @return a bi-consumer where the 1st parameter is ignored
	 */
	public static <P1, P2> BiConsumer<P1, P2> ignoreP1(Consumer<P2> fn) {
		return (p1, p2) -> fn.accept(p2);
	}
}
