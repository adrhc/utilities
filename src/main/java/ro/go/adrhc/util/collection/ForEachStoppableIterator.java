package ro.go.adrhc.util.collection;

import com.rainerhahnekamp.sneakythrow.functional.SneakyConsumer;

public interface ForEachStoppableIterator<T, E extends Exception> {
	void forEach(SneakyConsumer<? super T, E> unsafeVisitor) throws E;

	void stop();
}
