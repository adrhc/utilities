package ro.go.adrhc.util.value;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MutableValue<T> {
    private T value;

    public boolean hasValue() {
        return value != null;
    }
}
