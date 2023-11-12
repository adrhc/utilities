package ro.go.adrhc.util.collection;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@RequiredArgsConstructor
public class StreamCounter {
	private final StreamCountHolder count = new StreamCountHolder();

	public <T> Stream<T> countedStream(Stream<T> stream) {
		return stream.peek(count::increment);
	}

	public long getCount() {
		return count.getCount();
	}

	public void reset() {
		count.count = 0;
	}

	@Getter
	private static class StreamCountHolder {
		private long count;

		public void increment(Object o) {
			count++;
		}
	}
}
