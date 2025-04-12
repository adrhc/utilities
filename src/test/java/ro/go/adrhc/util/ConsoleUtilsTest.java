package ro.go.adrhc.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static ro.go.adrhc.util.ConsoleUtils.*;

@Slf4j
class ConsoleUtilsTest {
	@Test
	void test() {
		log.info("\n{}", underline("underline text"));
		log.info("\n{}", underlineGreen("underline green text"));
		log.info("\n{}", underlineYellow("underline yellow text"));
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