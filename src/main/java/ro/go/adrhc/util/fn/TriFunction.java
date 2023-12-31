package ro.go.adrhc.util.fn;

import java.util.function.BiFunction;

public interface TriFunction<T1, T2, T3, R> {
    static <T1, T2, T3, R> BiFunction<T1, T2, R> curry(TriFunction<T1, T2, T3, R> triFn, T3 t3) {
        return (t1, t2) -> triFn.apply(t1, t2, t3);
    }

    static <T1, T2, T3, R> BiFunction<T2, T3, R> curryT1(TriFunction<T1, T2, T3, R> triFn, T1 t1) {
        return (t2, t3) -> triFn.apply(t1, t2, t3);
    }

    R apply(T1 t1, T2 t2, T3 t3);
}
