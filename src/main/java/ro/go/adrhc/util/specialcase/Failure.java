package ro.go.adrhc.util.specialcase;

@FunctionalInterface
public interface Failure {
	boolean isFailure();

	default boolean isSuccess() {
		return !isFailure();
	}
}
