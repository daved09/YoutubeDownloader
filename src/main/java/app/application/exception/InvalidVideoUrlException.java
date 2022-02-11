package app.application.exception;

import java.net.MalformedURLException;
import java.net.URL;

public class InvalidVideoUrlException extends Throwable {

	public InvalidVideoUrlException(String url) {
		super("Invalid url: " + url);
	}

	public InvalidVideoUrlException(Exception e, String url) {
		super("Invalid url: " + url, e);
	}
}
