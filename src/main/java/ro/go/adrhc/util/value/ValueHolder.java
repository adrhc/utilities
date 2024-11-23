package ro.go.adrhc.util.value;

public record ValueHolder<T>(T value) {
	public boolean isNull() {
		return value == null;
	}
}
