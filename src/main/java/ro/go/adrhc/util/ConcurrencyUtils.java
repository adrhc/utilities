package ro.go.adrhc.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Stream;

@Slf4j
public class ConcurrencyUtils {
	public static <T> Stream<T> safelyGetAll(Stream<CompletableFuture<T>> futures) {
		return joinAll(futures).map(ConcurrencyUtils::safelyGet).flatMap(Optional::stream);
	}

	private static <T> Stream<CompletableFuture<T>>
	joinAll(Stream<CompletableFuture<T>> futures) {
		CompletableFuture<T>[] futuresArray = futures.toArray(CompletableFuture[]::new);
		CompletableFuture.allOf(futuresArray).join();
		return Arrays.stream(futuresArray);
	}

	private static <T> Optional<T> safelyGet(Future<T> future) {
		try {
			return Optional.ofNullable(future.get());
		} catch (InterruptedException | ExecutionException e) {
			log.error(e.getMessage(), e);
		}
		return Optional.empty();
	}
}
