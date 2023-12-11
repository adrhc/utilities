package ro.go.adrhc.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Assert {
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }
}
