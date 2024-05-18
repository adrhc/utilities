package ro.go.adrhc.util.io;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FilenameUtilsTest {
	@Test
	void sanitize() {
		assertEquals("", FilenameUtils.sanitize("<>:\"/\\|?*!;{}"));
		assertEquals("x", FilenameUtils.sanitize("<>:\"/\\| x ?*!;{}"));
		assertEquals("x", FilenameUtils.sanitize(" x <>:\"/\\|?*!;{}"));
		assertEquals("x", FilenameUtils.sanitize("<>:\"/\\|?*!;{} x "));
	}
}