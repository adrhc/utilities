package ro.go.adrhc.util.runtime;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@UtilityClass
@Slf4j
public class ProcessUtils {
	public static void logOutcome(Process process) throws IOException {
		BufferedReader outputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line;
		while ((line = outputReader.readLine()) != null) {
			log.debug(line);
		}
	}
}
