package ro.go.adrhc.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Slf4jUtils {
	public static void logError(org.slf4j.Logger logger, Exception e) {
		logger.error(e.getMessage(), e);
	}
}
