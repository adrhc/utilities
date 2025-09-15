package ro.go.adrhc.util.value;

import lombok.*;

import java.util.Optional;
import java.util.function.Consumer;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MutableValue<T> {
	private T value;

	public void ifPresent(Consumer<T> consumer) {
		if (value != null) {
			consumer.accept(value);
		}
	}

	public Optional<T> toOptional() {
		return Optional.ofNullable(value);
	}

	public boolean hasValue() {
		return value != null;
	}

	public boolean isEmpty() {
		return value == null;
	}
}
