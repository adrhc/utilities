package ro.go.adrhc.util.specialcase;

@FunctionalInterface
public interface Broken {
	boolean isBroken();

	default boolean isOk() {
		return !isBroken();
	}
}
