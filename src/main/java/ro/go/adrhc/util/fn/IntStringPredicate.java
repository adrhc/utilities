package ro.go.adrhc.util.fn;

@FunctionalInterface
public interface IntStringPredicate {
	boolean test(int value, String text);
}
