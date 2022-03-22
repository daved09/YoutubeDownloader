package app.application.utils;

import app.application.exception.InvalidPlaylistUrlException;
import app.application.exception.InvalidVideoUrlException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class YoutubeIdExtractorTest {

	@Mock
	private YoutubeUrlValidator youtubeUrlValidator;
	@Mock
	private LinkParameterBuilder linkParameterBuilder;

	private final Map<String, String> playlistMap = Map.of("list", "Test123");
	private final Map<String, String> videoMap = Map.of("v", "video");

	@Test
	public void testValidUrlGivesValidVideoId() throws MalformedURLException, InvalidVideoUrlException {
		String videoLink = "https://www.youtube.com/watch?v=video";
		URL videoUrl = new URL(videoLink);
		when(youtubeUrlValidator.isShortUrl(videoUrl.getHost())).thenReturn(false);
		when(linkParameterBuilder.buildParameterMap(videoUrl.getQuery())).thenReturn(videoMap);

		YoutubeIdExtractor youtubeIdExtractor = new YoutubeIdExtractor(youtubeUrlValidator, linkParameterBuilder);
		String videoId = youtubeIdExtractor.getVideoIdFromLink(videoLink);

		assertEquals(videoMap.get("v"), videoId);

	}


	@Test
	public void testValidShortUrlGivesValidVideoId() throws MalformedURLException, InvalidVideoUrlException {
		String videoLink = "https://youtu.be/video";
		URL videoUrl = new URL(videoLink);
		when(youtubeUrlValidator.isShortUrl(videoUrl.getHost())).thenReturn(true);
		when(linkParameterBuilder.buildParameterMap(videoUrl.getQuery())).thenReturn(videoMap);

		YoutubeIdExtractor youtubeIdExtractor = new YoutubeIdExtractor(youtubeUrlValidator, linkParameterBuilder);
		String videoId = youtubeIdExtractor.getVideoIdFromLink(videoLink);

		assertEquals(videoMap.get("v"), videoId);

	}

	@Test(expected = InvalidVideoUrlException.class)
	public void testInvalidUrlThrowsException() throws InvalidVideoUrlException {
		String videoLink = "InvalidUrl";

		YoutubeIdExtractor youtubeIdExtractor = new YoutubeIdExtractor(youtubeUrlValidator, linkParameterBuilder);
		youtubeIdExtractor.getVideoIdFromLink(videoLink);

	}

	@Test
	public void testValidPlaylistUrlGivesValidPlaylistId() throws MalformedURLException, InvalidPlaylistUrlException {
		String playlistLink = "https://www.youtube.com/playlist?list=myPlaylist";
		URL playlistUrl = new URL(playlistLink);
		when(linkParameterBuilder.buildParameterMap(playlistUrl.getQuery())).thenReturn(playlistMap);

		YoutubeIdExtractor youtubeIdExtractor = new YoutubeIdExtractor(youtubeUrlValidator, linkParameterBuilder);
		String playlistId = youtubeIdExtractor.getPlayListIdFromLink(playlistLink);

		assertEquals(playlistMap.get("list"), playlistId);
	}

	@Test(expected = InvalidPlaylistUrlException.class)
	public void testInvalidPlaylistUrlThrowsException() throws InvalidPlaylistUrlException {
		String playlistLink = "InvalidUrl";

		YoutubeIdExtractor youtubeIdExtractor = new YoutubeIdExtractor(youtubeUrlValidator, linkParameterBuilder);
		youtubeIdExtractor.getPlayListIdFromLink(playlistLink);
	}

}