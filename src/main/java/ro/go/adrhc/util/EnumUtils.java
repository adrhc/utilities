package ro.go.adrhc.util;

import lombok.experimental.UtilityClass;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class EnumUtils {
    public static <E extends Enum<E>> Set<String> toNamesSet(Class<E> enumClass) {
        return EnumSet.allOf(enumClass)
                .stream()
                .map(Enum::name)
                .collect(Collectors.toSet());
    }
}
