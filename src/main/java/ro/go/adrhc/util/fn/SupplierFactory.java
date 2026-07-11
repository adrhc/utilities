package ro.go.adrhc.util.fn;

import lombok.experimental.UtilityClass;

import java.util.function.Supplier;

import static ro.go.adrhc.util.fn.RunnableUtils.runAndReturnNull;

@UtilityClass
public class SupplierFactory {
	public static <T> Supplier<T> toNullSupplier(Runnable runnable) {
		return () -> runAndReturnNull(runnable);
	}
}
