package ro.go.adrhc.util.optional;

import ro.go.adrhc.util.ObjectHolder;

import java.util.stream.Stream;

public interface OptionalStream<T> extends Stream<T>, ObjectHolder<Stream<T>>, OptionalStatus {
}
