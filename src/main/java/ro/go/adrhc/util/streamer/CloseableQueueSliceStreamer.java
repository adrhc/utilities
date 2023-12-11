package ro.go.adrhc.util.streamer;

import lombok.RequiredArgsConstructor;
import ro.go.adrhc.util.concurrency.lock.LocksCollection;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

@RequiredArgsConstructor
class CloseableQueueSliceStreamer implements AutoCloseable {
    private final Lock addElementLock = new ReentrantLock();
    private final Lock markChunkEndLock = new ReentrantLock();
    private final LocksCollection locks = LocksCollection.of(addElementLock, markChunkEndLock);
    private final QueueSliceStreamer queueSliceStreamer;
    private final Runnable stopTrigger;
    private boolean closed;

    public static CloseableQueueSliceStreamer create(Runnable stopTrigger) {
        return new CloseableQueueSliceStreamer(new QueueSliceStreamer(), stopTrigger);
    }

    public void addElement(Object t) {
        doIfNotClosed(addElementLock, () -> queueSliceStreamer.put(t));
    }

    public void markChunkEnd() {
        doIfNotClosed(markChunkEndLock, queueSliceStreamer::startNewSlice);
    }

    public <T> Stream<T> streamChunk() {
        return queueSliceStreamer.streamCurrentSlice();
    }

    public void close() {
//			log.info("closing ...");
        locks.execute(this::doClose);
//			log.info("closed");
    }

    private void doIfNotClosed(Lock lock, Runnable runnable) {
        if (closed) {
            return;
        }
        lock.lock();
        try {
            if (!closed) {
                runnable.run();
            }
        } finally {
            lock.unlock();
        }
    }

    public void doClose() {
        closed = true;
        queueSliceStreamer.clear();
        stopTrigger.run();
    }
}
