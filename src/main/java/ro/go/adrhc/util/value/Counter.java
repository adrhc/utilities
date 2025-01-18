package ro.go.adrhc.util.value;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Counter {
	private int count;

	public void increment() {
		count++;
	}

	public void decrement() {
		count--;
	}

	public void reset() {
		count = 0;
	}

	public boolean isPositive() {
		return count > 0;
	}
}
