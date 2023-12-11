package ro.go.adrhc.util.collection.visitable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class AbstractStoppableVisitable<T> implements StoppableVisitable<T> {
    private boolean stopped;

    @Override
    public void stop() {
        this.stopped = true;
    }
}
