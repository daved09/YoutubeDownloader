package app.application.utils

import app.application.exception.InvalidPlaylistUrlException
import app.application.exception.InvalidVideoUrlException
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.net.MalformedURLException
import java.net.URL
import java.util.Map

@RunWith(MockitoJUnitRunner::class)
class YoutubeIdExtractorTest {
    @Mock
    private val youtubeUrlValidator: YoutubeUrlValidator? = null

    @Mock
    private val linkParameterBuilder: LinkParameterBuilder? = null

    private val playlistMap = Map.of("list", "Test123")
    private val videoMap = Map.of("v", "video")

    @Test
    @Throws(MalformedURLException::class, InvalidVideoUrlException::class)
    fun testValidUrlGivesValidVideoId() {
        val videoLink = "https://www.youtube.com/watch?v=video"
        val videoUrl = URL(videoLink)
        Mockito.`when`(youtubeUrlValidator!!.isShortUrl(videoUrl.host)).thenReturn(false)
        Mockito.`when`(linkParameterBuilder!!.buildParameterMap(videoUrl.query)).thenReturn(videoMap)
        val youtubeIdExtractor = YoutubeIdExtractor(youtubeUrlValidator, linkParameterBuilder)
        val videoId = youtubeIdExtractor.getVideoIdFromLink(videoLink)
        Assert.assertEquals(videoMap["v"], videoId)
    }

    @Test
    @Throws(MalformedURLException::class, InvalidVideoUrlException::class)
    fun testValidShortUrlGivesValidVideoId() {
        val videoLink = "https://youtu.be/video"
        val videoUrl = URL(videoLink)
        Mockito.`when`(youtubeUrlValidator!!.isShortUrl(videoUrl.host)).thenReturn(true)
        Mockito.`when`(linkParameterBuilder!!.buildParameterMap(videoUrl.query)).thenReturn(videoMap)
        val youtubeIdExtractor = YoutubeIdExtractor(youtubeUrlValidator, linkParameterBuilder)
        val videoId = youtubeIdExtractor.getVideoIdFromLink(videoLink)
        Assert.assertEquals(videoMap["v"], videoId)
    }

    @Test(expected = InvalidVideoUrlException::class)
    @Throws(InvalidVideoUrlException::class)
    fun testInvalidUrlThrowsException() {
        val videoLink = "InvalidUrl"
        val youtubeIdExtractor = YoutubeIdExtractor(youtubeUrlValidator!!, linkParameterBuilder!!)
        youtubeIdExtractor.getVideoIdFromLink(videoLink)
    }

    @Test
    @Throws(MalformedURLException::class, InvalidPlaylistUrlException::class)
    fun testValidPlaylistUrlGivesValidPlaylistId() {
        val playlistLink = "https://www.youtube.com/playlist?list=myPlaylist"
        val playlistUrl = URL(playlistLink)
        Mockito.`when`(linkParameterBuilder!!.buildParameterMap(playlistUrl.query)).thenReturn(playlistMap)
        val youtubeIdExtractor = YoutubeIdExtractor(youtubeUrlValidator!!, linkParameterBuilder)
        val playlistId = youtubeIdExtractor.getPlayListIdFromLink(playlistLink)
        Assert.assertEquals(playlistMap["list"], playlistId)
    }

    @Test(expected = InvalidPlaylistUrlException::class)
    @Throws(InvalidPlaylistUrlException::class)
    fun testInvalidPlaylistUrlThrowsException() {
        val playlistLink = "InvalidUrl"
        val youtubeIdExtractor = YoutubeIdExtractor(youtubeUrlValidator!!, linkParameterBuilder!!)
        youtubeIdExtractor.getPlayListIdFromLink(playlistLink)
    }
}