package ro.go.adrhc.util.streamer;

import ro.go.adrhc.util.collection.visitable.AbstractStoppableVisitable;

import java.util.function.Consumer;

public abstract class AbstractVisitorAwareStoppableVisitable<T> extends AbstractStoppableVisitable<T> {
	protected Consumer<? super T> visitor;
}
