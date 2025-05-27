package ro.go.adrhc.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StorageMeasureUnitUtils {
	//	public static final long KILO_BYTE = 1024;
//	public static final double KILO_BYTE_DOUBLE = 1024d;
//	public static final long MEGA_BYTE = 1024 * KILO_BYTE;
//	public static final double MEGA_BYTE_DOUBLE = 1d * MEGA_BYTE;
//	public static final long GIGA_BYTE = 1024 * MEGA_BYTE;
//	public static final double GIGA_BYTE_DOUBLE = 1d * GIGA_BYTE;
	public static final long KILO_BYTE = 1000;
	public static final double KILO_BYTE_DOUBLE = 1000d;
	public static final long MEGA_BYTE = 1000 * KILO_BYTE;
	public static final double MEGA_BYTE_DOUBLE = 1d * MEGA_BYTE;
	public static final long GIGA_BYTE = 1000 * MEGA_BYTE;
	public static final double GIGA_BYTE_DOUBLE = 1d * GIGA_BYTE;

	public static long kbToBytes(int megaBytes) {
		return megaBytes * KILO_BYTE;
	}

	public static long mbToBytes(int megaBytes) {
		return megaBytes * MEGA_BYTE;
	}

	public static long gbToBytes(int gigaBytes) {
		return gigaBytes * GIGA_BYTE;
	}

	public static long gbToMB(int gigaBytes) {
		return gigaBytes * 1024L;
	}

	public static double bytesToKB(long bytes) {
		return bytes / KILO_BYTE_DOUBLE;
	}

	public static double bytesToMB(long bytes) {
		return bytes / MEGA_BYTE_DOUBLE;
	}

	public static double bytesToGB(long bytes) {
		return bytes / GIGA_BYTE_DOUBLE;
	}

	public static String kbFormatted(long megaBytes) {
		return "%.3f".formatted(bytesToKB(megaBytes));
	}

	public static String mbFormatted(long megaBytes) {
		return "%.3f".formatted(bytesToMB(megaBytes));
	}

	public static String gbFormatted(long megaBytes) {
		return "%.3f".formatted(bytesToGB(megaBytes));
	}

	/*public static String formatByteCount(long byteCount) {
		return "%s GB, %s MB, %d B".formatted(gbFormatted(byteCount), mbFormatted(byteCount), byteCount);
	}*/

	public static String formatByteCount(long size) {
		if (size < KILO_BYTE) {
			return "%s KB".formatted(kbFormatted(size));
		} else if (size < MEGA_BYTE) {
			return "%s MB".formatted(mbFormatted(size));
		} else {
			return "%s GB".formatted(gbFormatted(size));
		}
	}
}
