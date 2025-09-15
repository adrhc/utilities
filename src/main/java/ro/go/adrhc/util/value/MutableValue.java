package ro.go.adrhc.util.value;

import lombok.*;

import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MutableValue<T> {
	private T value;

	public void ifPresent(Runnable runnable) {
		if (value != null) {
			runnable.run();
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
