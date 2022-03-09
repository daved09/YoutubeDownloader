package app.application.exception;

public class InvalidVideoUrlException extends Exception {

	public InvalidVideoUrlException(String url) {
		super("Invalid url: " + url);
	}

	public InvalidVideoUrlException(Exception e, String url) {
		super("Invalid url: " + url, e);
	}
}
