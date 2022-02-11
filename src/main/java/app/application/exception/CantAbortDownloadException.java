package app.application.exception;

public class CantAbortDownloadException extends Exception {

	public CantAbortDownloadException(Exception e) {
		super(e);
	}
}
