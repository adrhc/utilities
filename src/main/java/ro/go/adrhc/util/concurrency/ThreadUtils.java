package ro.go.adrhc.util.concurrency;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@UtilityClass
@Slf4j
public class ThreadUtils {
    public static void safelySleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

    public static void safelySleep(Duration duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void safelyJoin(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }
}
