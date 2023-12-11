package ro.go.adrhc.util.concurrency.lock;

import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

@RequiredArgsConstructor
public class SmartCondition implements Condition {
    private final SmartLock lock;
    private final Condition condition;

    public void signalSuccessfulCompletion(Runnable runnable) {
        lock.lock();
        try {
            runnable.run();
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void await() throws InterruptedException {
        condition.await();
    }

    @Override
    public void awaitUninterruptibly() {
        condition.awaitUninterruptibly();
    }

    @Override
    public long awaitNanos(long nanosTimeout) throws InterruptedException {
        return condition.awaitNanos(nanosTimeout);
    }

    @Override
    public boolean await(long time, TimeUnit unit) throws InterruptedException {
        return condition.await(time, unit);
    }

    @Override
    public boolean awaitUntil(Date deadline) throws InterruptedException {
        return condition.awaitUntil(deadline);
    }

    @Override
    public void signal() {
        condition.signal();
    }

    @Override
    public void signalAll() {
        condition.signalAll();
    }
}
