package ro.go.adrhc.util.fn;

import com.rainerhahnekamp.sneakythrow.functional.SneakyPredicate;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class PredicateUtils {
	public static <T> boolean failToFalse(SneakyPredicate<T, ?> predicate, T t) {
		try {
			return predicate.test(t);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return false;
	}

	public static <T> boolean eqInstance(T t1, T t2) {
		return t1 == t2;
	}

	public static <V> boolean toFalse(V v) {
		return false;
	}

	public static <V> boolean toTrue(V v) {
		return true;
	}
}
