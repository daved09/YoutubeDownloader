package app.application.utils;

import app.application.exception.InvalidPlaylistUrlException;
import app.application.exception.InvalidVideoUrlException;
import org.junit.Test;

import static org.junit.Assert.*;

public class YoutubeUrlValidatorTest {

	@Test
	public void testUrlValidatorReturnsValidOnYoutubeUrl() throws InvalidVideoUrlException {
		String url = "https://www.youtube.com/watch?v=j151FYZT4ZE";
		YoutubeUrlValidator youtubeUrlValidator = new YoutubeUrlValidator();
		youtubeUrlValidator.checkVideoUrl(url);

		//Should not fail
	}

	@Test
	public void testUrlValidatorReturnsValidOnSortYoutubeUrl() throws InvalidVideoUrlException {
		String url = "https://youtu.be/j151FYZT4ZE";
		YoutubeUrlValidator youtubeUrlValidator = new YoutubeUrlValidator();
		youtubeUrlValidator.checkVideoUrl(url);

		//Should not fail
	}

	@Test
	public void testUrlValidatorReturnsValidOnSortYoutubeUrlAndTimestamp() throws InvalidVideoUrlException {
		String url = "https://youtu.be/j151FYZT4ZE?t=20";
		YoutubeUrlValidator youtubeUrlValidator = new YoutubeUrlValidator();
		youtubeUrlValidator.checkVideoUrl(url);

		//Should not fail
	}

	@Test
	public void testUrlValidatorReturnsValidOnYoutubeUrlAndTimestamp() throws InvalidVideoUrlException {
		String url = "https://www.youtube.com/watch?v=j151FYZT4ZE&t=20";
		YoutubeUrlValidator youtubeUrlValidator = new YoutubeUrlValidator();
		youtubeUrlValidator.checkVideoUrl(url);

		//Should not fail
	}

	@Test(expected = InvalidVideoUrlException.class)
	public void testUrlValidatorThrowsExceptionOnInvalidUrl() throws InvalidVideoUrlException {
		String url = "NoUrl";
		YoutubeUrlValidator youtubeUrlValidator = new YoutubeUrlValidator();
		youtubeUrlValidator.checkVideoUrl(url);

		//Should not fail
	}

	@Test
	public void testUrlValidatorReturnsValidOnPlaylistUrl() throws InvalidPlaylistUrlException {
		String url = "https://www.youtube.com/playlist?list=PL-_dGWmdQ0CJMcmQ5ximmrusq-0wjPbh8";
		YoutubeUrlValidator youtubeUrlValidator = new YoutubeUrlValidator();
		youtubeUrlValidator.checkPlaylistUrl(url);

		//Should not fail
	}

	@Test
	public void testUrlValidatorThrowsExceptionOnInvalidPlaylistUrl() throws InvalidPlaylistUrlException {
		String url = "https://www.youtube.com/playlistlist=PL-_dGWmdQ0CJMcmQ5ximmrusq-0wjPbh8";
		YoutubeUrlValidator youtubeUrlValidator = new YoutubeUrlValidator();
		youtubeUrlValidator.checkPlaylistUrl(url);

		//Should not fail
	}


}