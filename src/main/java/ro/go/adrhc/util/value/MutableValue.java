package ro.go.adrhc.util.value;

import lombok.*;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MutableValue<T> {
	private T value;

	/**
	 * If "replacer" is null, then the replacement value is considered null!
	 *
	 * @param replacer is used to determine the new value
	 * @return the previous value
	 */
	public T replace(UnaryOperator<T> replacer) {
		T oldValue = value;
		value = replacer == null ? null : replacer.apply(value);
		return oldValue;
	}

	/**
	 * If the value is null the predicate is not used and false is returned!
	 */
	public boolean test(Predicate<? super T> predicate) {
		return value != null && predicate.test(value);
	}

	public void ifNotEmpty(Consumer<T> consumer) {
		if (value != null) {
			consumer.accept(value);
		}
	}

	public void ifNotEmptyOrElse(Consumer<? super T> consumer, Runnable runnable) {
		if (value == null) {
			runnable.run();
		} else {
			consumer.accept(value);
		}
	}

	public void clear() {
		value = null;
	}

	public boolean isEmpty() {
		return value == null;
	}

	public boolean isNotEmpty() {
		return value != null;
	}

	public Optional<T> toOptional() {
		return Optional.ofNullable(value);
	}
}
