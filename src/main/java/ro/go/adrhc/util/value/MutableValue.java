package ro.go.adrhc.util.value;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MutableValue<T> {
	private T value;

	public Optional<T> getOptionalValue() {
		return Optional.ofNullable(value);
	}

	public boolean hasValue() {
		return value != null;
	}

	public boolean isEmpty() {
		return value == null;
	}
}
