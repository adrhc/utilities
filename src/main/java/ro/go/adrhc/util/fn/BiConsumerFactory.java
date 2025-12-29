package ro.go.adrhc.util.fn;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@UtilityClass
@Slf4j
public class BiConsumerFactory {
	public static <P1, P2, P3> BiConsumer<P1, P2>
	toP1P2BiConsumer(TriConsumer<P1, P2, P3> fn, P3 p3) {
		return (p1, p2) -> fn.accept(p1, p2, p3);
	}

	/**
	 * @return a bi-consumer where the 1st parameter is ignored
	 */
	public static <P1, P2> BiConsumer<P1, P2> toIgnoredP1BiFn(Consumer<P2> fn) {
		return (p1, p2) -> fn.accept(p2);
	}
}
