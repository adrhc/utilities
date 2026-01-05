package ro.go.adrhc.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import ro.go.adrhc.util.text.FontStyle;

import static ro.go.adrhc.util.ConsoleUtils.*;

@Slf4j
class ConsoleUtilsTest {
	@Test
	void format() {
		log.info("\n{}", ConsoleUtils.format(
			new FontStyle[]{FontStyle.BOLD, FontStyle.ITALIC},
			"bold italic text"
		));
	}

	@Test
	void test() {
		log.info("\n{}", underline("italic text"));
		log.info("\n{}", underlineGreen("italic green text"));
		log.info("\n{}", underlineYellow("italic yellow text"));
		log.info("\n{}", italic("italic text"));
		log.info("\n{}", bold("bold text"));
		log.info("\n{}", red("red text"));
		log.info("\n{}", green("green text"));
		log.info("\n{}", yellow("yellow text"));
		log.info("\n{}", blue("blue text"));
		log.info("\n{}", boldRed("bold red text"));
		log.info("\n{}", boldGreen("bold green text"));
		log.info("\n{}", boldYellow("bold yellow text"));
		log.info("\n{}", boldBlue("bold blue text"));
	}
}