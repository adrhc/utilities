package ro.go.adrhc.util.specialcase;

@FunctionalInterface
public interface BrokenObject {
	boolean isBroken();

	default boolean isCorrect() {
		return !isBroken();
	}
}
