package app.application.exception;

public class VideoInformationNotAvailable extends RuntimeException {

	public VideoInformationNotAvailable() {
		super("The requesting video information are not available.");
	}
}
