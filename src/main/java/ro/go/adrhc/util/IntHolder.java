package ro.go.adrhc.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class IntHolder {
    private int value;

    public void subtract(int other) {
        this.value -= other;
    }

    public boolean isPositive() {
        return value > 0;
    }
}
