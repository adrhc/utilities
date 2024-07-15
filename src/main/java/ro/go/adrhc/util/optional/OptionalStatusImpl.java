package ro.go.adrhc.util.optional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OptionalStatusImpl implements OptionalStatus {
	protected final boolean missing;

	@Override
	public boolean isPresent() {
		return false;
	}
}
