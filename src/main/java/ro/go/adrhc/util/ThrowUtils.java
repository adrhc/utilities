package ro.go.adrhc.util;

import lombok.experimental.UtilityClass;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

@UtilityClass
public class ThrowUtils {
    public static <T extends Throwable> void throwIf(
            Supplier<T> throwableSupplier, BooleanSupplier shouldThrow) throws T {
        throwIf(throwableSupplier, shouldThrow.getAsBoolean());
    }

    public static <T extends Throwable> void throwIf(
            Supplier<T> throwableSupplier, boolean shouldThrow) throws T {
        if (shouldThrow) {
            throw throwableSupplier.get();
        }
    }

    public static <T extends Throwable> void throwIf(
            T throwable, boolean shouldThrow) throws T {
        if (shouldThrow) {
            throw throwable;
        }
    }
}
