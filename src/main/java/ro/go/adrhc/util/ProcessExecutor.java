package ro.go.adrhc.util;

import com.rainerhahnekamp.sneakythrow.functional.SneakyFunction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import ro.go.adrhc.util.text.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static java.util.concurrent.TimeUnit.SECONDS;

@RequiredArgsConstructor
@Slf4j
public class ProcessExecutor {
	private final Duration timeout;

	public Optional<Boolean> run(List<String> processParams) {
		return execute(_ -> Boolean.TRUE, processParams);
	}

	public Optional<String> execute(List<String> processParams) {
		return execute(p -> IOUtils.toString(
				p.getInputStream(), StandardCharsets.UTF_8), processParams)
				.filter(StringUtils::hasText);
	}

	private <T> Optional<T> execute(SneakyFunction<Process, T, IOException> resultMapper,
			List<String> processParams) {
		ProcessBuilder processBuilder = createProcessBuilder(processParams);
//		log.info("\nprocess command:\n{}", concat(" ", processBuilder.command()));

		try {
			Process process = processBuilder.start();

			T result = resultMapper.apply(process);
			// log.info("\nprocess execution result:\n{}", result);

			// process waiting must happen after reading its output!
			process.waitFor(timeout.getSeconds(), SECONDS);

			return Optional.of(result);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			log.error("\nFailed to execute the process!");
		}
		return Optional.empty();
	}

	private ProcessBuilder createProcessBuilder(List<String> processParams) {
		ProcessBuilder processBuilder = new ProcessBuilder(processParams);
		processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
		return processBuilder;
	}
}
