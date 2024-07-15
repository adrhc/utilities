package ro.go.adrhc.util.optional;

import ro.go.adrhc.util.ObjectHolder;

import java.util.List;

public interface OptionalList<T> extends List<T>, ObjectHolder<List<T>>, OptionalStatus {
}
