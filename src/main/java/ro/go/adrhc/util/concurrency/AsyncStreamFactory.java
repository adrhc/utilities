package ro.go.adrhc.util.concurrency;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.ExecutorService;

@RequiredArgsConstructor
public class AsyncStreamFactory<T> {
	private final ExecutorService adminExecutorService;

	public AsyncStream<T> create() {
		return new AsyncStream<>(adminExecutorService);
	}
}
