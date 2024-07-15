package ro.go.adrhc.util.optional;

import ro.go.adrhc.util.ObjectHolder;

import java.util.Set;

public interface OptionalSet<T> extends Set<T>, ObjectHolder<Set<T>>, OptionalStatus {
}
