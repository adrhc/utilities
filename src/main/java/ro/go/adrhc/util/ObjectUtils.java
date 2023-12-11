package ro.go.adrhc.util;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.Objects;

@UtilityClass
public class ObjectUtils {
    @SafeVarargs
    public static <T> T firstNotNull(T... t) {
        return Arrays.stream(t).filter(Objects::nonNull).findFirst().orElse(null);
    }

    public static <T> T cast(Object o) {
        return (T) o;
    }
}
