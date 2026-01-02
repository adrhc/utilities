package ro.go.adrhc.util.fn;

@FunctionalInterface
public interface ThrowableConsumer<O, T extends Throwable> {
	void accept(O o) throws T;
}
