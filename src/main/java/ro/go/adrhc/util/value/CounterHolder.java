package ro.go.adrhc.util.value;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CounterHolder {
	private int value;

	public void increment() {
		value++;
	}

	public void decrement() {
		value--;
	}

	public void reset() {
		value = 0;
	}
}
