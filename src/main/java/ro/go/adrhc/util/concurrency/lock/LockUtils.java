package ro.go.adrhc.util.concurrency.lock;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.function.Consumer;

@UtilityClass
@Slf4j
public class LockUtils {
    public static void safelyAwait(Condition condition) {
        safelyAwait(condition, e -> log.error(e.getMessage(), e));
    }

    public static void safelyAwait(Condition condition,
            Consumer<InterruptedException> interruptedExceptionConsumer) {
        try {
            condition.await();
        } catch (InterruptedException e) {
            interruptedExceptionConsumer.accept(e);
        }
    }
}
