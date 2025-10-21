package ro.go.adrhc.util.value;

import lombok.*;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MutableValue<T> {
	private T value;

	/**
	 * If the value is null the predicate is not invoked and false is returned!
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
