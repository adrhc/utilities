package ro.go.adrhc.util.collection;

public interface StoppableVisitable<T> extends Visitable<T> {
	void stop();
}
