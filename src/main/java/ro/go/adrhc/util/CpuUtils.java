package ro.go.adrhc.util;

public class CpuUtils {
    public static int cpuCoresMultipliedBy(String multiplier) {
        return Runtime.getRuntime().availableProcessors() *
                Integer.parseInt(multiplier.substring(0, multiplier.length() - 1));
    }
}
