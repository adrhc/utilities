package ro.go.adrhc.util.pair;

import lombok.experimental.Accessors;

@Accessors(fluent = true)
public record LongRightPair<L>(L left, long right) {
}
