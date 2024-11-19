package ro.go.adrhc.util.text;

import lombok.RequiredArgsConstructor;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static ro.go.adrhc.util.text.StringUtils.concat;

@RequiredArgsConstructor
public class MessageFormats {
	private final List<MessageFormat> messageFormats;

	public static MessageFormats of(Collection<String> patterns) {
		return new MessageFormats(patterns.stream().map(MessageFormat::new).toList());
	}

	public Optional<Object[]> parse(String text) {
		for (MessageFormat format : messageFormats) {
			try {
				return Optional.of(format.parse(text));
			} catch (ParseException e) {
				// do nothing
			}
		}
		return Optional.empty();
	}

	public boolean matches(String text) {
		return parse(text).isPresent();
	}

	@Override
	public String toString() {
		return concat(MessageFormat::toPattern, messageFormats);
	}
}
