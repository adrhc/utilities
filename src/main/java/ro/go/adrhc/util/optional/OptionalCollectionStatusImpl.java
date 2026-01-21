package ro.go.adrhc.util.optional;

import lombok.Getter;

@Getter
public class OptionalCollectionStatusImpl
	extends OptionalStatusImpl implements OptionalCollectionStatus {
	protected final boolean incomplete;

	public OptionalCollectionStatusImpl(boolean missing, boolean incomplete) {
		super(missing);
		this.incomplete = incomplete;
	}

	@Override
	public boolean isComplete() {
		return !incomplete;
	}
}
