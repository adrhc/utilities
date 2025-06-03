package ro.go.adrhc.util;

@FunctionalInterface
public interface StaleProne {
	boolean isStale();
}
