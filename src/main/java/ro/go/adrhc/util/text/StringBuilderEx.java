package ro.go.adrhc.util.text;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import static ro.go.adrhc.util.text.StringUtils.hasText;

@RequiredArgsConstructor
public class StringBuilderEx {
	private final StringBuilder sb = new StringBuilder();
	private final String separator;

	public static StringBuilderEx spaceSeparator() {
		return new StringBuilderEx(" ");
	}

	@Override
	public String toString() {
		return sb.toString();
	}

	public StringBuilderEx appendKeyValueWord(@NonNull String key, String value) {
		if (hasText(value)) {
			append("%s \"%s\"".formatted(key, value));
		}
		return this;
	}

	public StringBuilderEx appendWord(String text) {
		if (hasText(text)) {
			return append("\"%s\"".formatted(text));
		} else {
			return this;
		}
	}

	public StringBuilderEx appendKeyValue(@NonNull String key, String value) {
		if (hasText(value)) {
			append("%s %s".formatted(key, value));
		}
		return this;
	}

	public StringBuilderEx append(String text) {
		if (hasText(text)) {
			appendSeparator();
			sb.append(text);
		}
		return this;
	}

	private void appendSeparator() {
		if (!sb.isEmpty()) {
			sb.append(separator);
		}
	}
}
