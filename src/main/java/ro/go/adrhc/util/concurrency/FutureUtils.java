package ro.go.adrhc.util.concurrency;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Stream;

@UtilityClass
@Slf4j
public class FutureUtils {
	public static void safelyWaitAll(ExecutorService executorService, Runnable... runnable) {
		safelyWaitAll(Arrays.stream(runnable).map(executorService::submit));
	}

	public static void safelyWaitAll(Future<?>... future) {
		safelyWaitAll(Arrays.stream(future));
	}

	public static void safelyWaitAll(Stream<? extends Future<?>> futures) {
		futures.forEach(FutureUtils::safelyWait);
	}

	/**
	 * @param future is allowed to be null
	 */
	public static void safelyWait(Future<?> future) {
		if (future != null) {
			try {
				future.get();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	public static <T> Stream<T> safelyGetAll(Stream<? extends CompletableFuture<T>> futures) {
		return safelyWaitAndGetThemAll(futures).map(FutureUtils::safelyGet).flatMap(Optional::stream);
	}

	public static <T> Optional<T> safelyGet(Future<T> future) {
		try {
			return Optional.ofNullable(future.get());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return Optional.empty();
	}

	private static <T, F extends Future<T>> Stream<F> safelyWaitAndGetThemAll(Stream<F> futures) {
		List<F> futuresList = futures.toList();
		safelyWaitAll(futuresList.stream());
		return futuresList.stream();
	}
}
