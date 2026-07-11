package ro.go.adrhc.util.fn;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RunnableUtils {
	public static <T> T runAndReturnNull(Runnable runnable) {
		runnable.run();
		return null;
	}
}
