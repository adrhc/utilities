package ro.go.adrhc.util.streamer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.go.adrhc.util.collection.CloseAwareStream;
import ro.go.adrhc.util.collection.visitable.StoppableVisitable;
import ro.go.adrhc.util.collection.visitable.Visitable;

import java.util.concurrent.ExecutorService;
import java.util.stream.Stream;

import static ro.go.adrhc.util.collection.visitable.StoppableVisitable.ignoreStop;

/**
 * Not thread safe!
 */
@RequiredArgsConstructor
@Slf4j
public class VisitableStructureStreamer<T> {
    private final ExecutorService executorService;
    private CloseableQueueSliceStreamer closeableStreamer;
    private StoppableVisitable<T> stoppableVisitable;

    public Stream<T> toStream(Visitable<T> visitable) {
        return toStream(ignoreStop(visitable));
    }

    public Stream<T> toStream(StoppableVisitable<T> stoppableVisitable) {
        this.stoppableVisitable = stoppableVisitable;
        this.closeableStreamer = CloseableQueueSliceStreamer.create(stoppableVisitable::stop);
        executorService.execute(this::collect); // async collect
        return new CloseAwareStream<>(closeableStreamer.streamChunk(), closeableStreamer::close);
    }

    protected void collect() {
//		log.info("begin elements collection");
        stoppableVisitable.accept(closeableStreamer::addElement);
//		log.info("elements collection completed");
        closeableStreamer.markChunkEnd();
//		log.info("after markChunkEnd");
    }
}
