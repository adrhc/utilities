package ro.go.adrhc.util.concurrency.lock;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;

@UtilityClass
@Slf4j
public class LockUtils {
    public static void safelyAwait(Condition condition) {
        try {
            condition.await();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }
}
