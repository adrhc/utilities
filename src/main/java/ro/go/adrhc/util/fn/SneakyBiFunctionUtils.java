package ro.go.adrhc.util.fn;

import com.rainerhahnekamp.sneakythrow.functional.SneakyBiFunction;
import com.rainerhahnekamp.sneakythrow.functional.SneakyFunction;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SneakyBiFunctionUtils {
	public static <T1, T2, R, E extends Exception> SneakyFunction<T2, R, E> curry(
			SneakyBiFunction<T1, T2, R, E> biFunction, T1 t1) {
		return t2 -> biFunction.apply(t1, t2);
	}
}
