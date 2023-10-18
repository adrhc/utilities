package adrhc.go.ro.util;

import java.util.Arrays;
import java.util.Objects;

public class ObjectUtils {
	@SafeVarargs
	public static <T> T firstNotNull(T... t) {
		return Arrays.stream(t).filter(Objects::nonNull).findFirst().orElse(null);
	}
}
