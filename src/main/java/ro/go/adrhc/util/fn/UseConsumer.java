package ro.go.adrhc.util.fn;

@FunctionalInterface
public interface UseConsumer<T> {
	void use(T t);
}
