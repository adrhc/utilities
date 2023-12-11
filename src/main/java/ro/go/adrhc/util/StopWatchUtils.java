package ro.go.adrhc.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.time.StopWatch;

@UtilityClass
public class StopWatchUtils {
    public static StopWatch start() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        return stopWatch;
    }
}
