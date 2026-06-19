package ro.go.adrhc.util.exception;

public class HiddenException extends RuntimeException {
	public HiddenException(Exception e) {
		super(e);
	}
}
