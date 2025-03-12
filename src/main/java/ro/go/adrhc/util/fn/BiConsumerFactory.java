package ro.go.adrhc.util.fn;

import lombok.experimental.UtilityClass;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@UtilityClass
public class BiConsumerFactory {
	/**
	 * Add a new, null, 1st parameter, to the received consumer.
	 */
	public static <P1, P2> BiConsumer<P1, P2> addP1(Consumer<P2> fn) {
		return (p1, p2) -> fn.accept(p2);
	}
}
