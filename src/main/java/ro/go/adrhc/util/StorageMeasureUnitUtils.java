package ro.go.adrhc.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StorageMeasureUnitUtils {
	public static final long KILO_BYTE = 1024;
	public static final double KILO_BYTE_DOUBLE = 1024d;
	public static final long MEGA_BYTE = 1024 * KILO_BYTE;
	public static final double MEGA_BYTE_DOUBLE = 1d * MEGA_BYTE;
	public static final long GIGA_BYTE = 1024 * MEGA_BYTE;
	public static final double GIGA_BYTE_DOUBLE = 1d * GIGA_BYTE;

	public static long mbToBytes(int megaBytes) {
		return megaBytes * MEGA_BYTE;
	}

	public static long gbToBytes(int gigaBytes) {
		return gigaBytes * GIGA_BYTE;
	}

	public static double bytesToGB(long bytes) {
		return bytes / GIGA_BYTE_DOUBLE;
	}

	public static double bytesToMB(long bytes) {
		return bytes / MEGA_BYTE_DOUBLE;
	}

	public static double bytesToKB(long bytes) {
		return bytes / KILO_BYTE_DOUBLE;
	}

	public static String gbFormatted(long megaBytes) {
		return "%.2f".formatted(megaBytes / GIGA_BYTE_DOUBLE);
	}

	public static String mbFormatted(long megaBytes) {
		return "%.2f".formatted(megaBytes / MEGA_BYTE_DOUBLE);
	}

	public static String formatByteCount(long byteCount) {
		return "%s GB, %s MB, %d B".formatted(gbFormatted(byteCount), mbFormatted(byteCount), byteCount);
	}
}
