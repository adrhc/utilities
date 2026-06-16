package ro.go.adrhc.util.fn;

import lombok.experimental.UtilityClass;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;

@UtilityClass
public class UnaryOperatorFactory {
	public static <P1> UnaryOperator<P1> toP1ResultUo(Consumer<P1> consumer) {
		return p1 -> {
			consumer.accept(p1);
			return p1;
		};
	}
}
