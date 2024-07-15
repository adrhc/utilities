package ro.go.adrhc.util.optional;

import ro.go.adrhc.util.ObjectHolder;

import java.util.Collection;

public interface OptionalCollection<T> extends
		Collection<T>, ObjectHolder<Collection<T>>, OptionalStatus {
}
