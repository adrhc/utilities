package ro.go.adrhc.util.pair;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
@Accessors(fluent = true)
public class LongRightPair<L> {
	protected final L left;
	protected final long right;
}
