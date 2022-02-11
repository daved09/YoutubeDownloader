package app.application.exception;

public class InvalidPlaylistUrlException extends Exception{

	public InvalidPlaylistUrlException(String url) {
		super("Invalid playlist url: " + url);
	}

	public InvalidPlaylistUrlException(Exception cause, String url) {
		super("Invalid playlist url: " + url, cause);
	}
}
