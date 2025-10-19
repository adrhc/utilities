package ro.go.adrhc.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionUtils {
	public static <E extends Exception> void
	throwIfClass(Class<E> exceptionClass, Exception exception) throws E {
		if (exceptionClass.isInstance(exception)) {
			throw (E) exception;
		}
	}
}
