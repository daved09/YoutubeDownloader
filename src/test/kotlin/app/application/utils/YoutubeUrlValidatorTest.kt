package app.application.utils

import app.application.exception.InvalidPlaylistUrlException
import app.application.exception.InvalidVideoUrlException
import org.junit.Test

class YoutubeUrlValidatorTest {
    @Test
    @Throws(InvalidVideoUrlException::class)
    fun testUrlValidatorReturnsValidOnYoutubeUrl() {
        val url = "https://www.youtube.com/watch?v=j151FYZT4ZE"
        val youtubeUrlValidator = YoutubeUrlValidator()
        youtubeUrlValidator.checkVideoUrl(url)

        //Should not fail
    }

    @Test
    @Throws(InvalidVideoUrlException::class)
    fun testUrlValidatorReturnsValidOnSortYoutubeUrl() {
        val url = "https://youtu.be/j151FYZT4ZE"
        val youtubeUrlValidator = YoutubeUrlValidator()
        youtubeUrlValidator.checkVideoUrl(url)

        //Should not fail
    }

    @Test
    @Throws(InvalidVideoUrlException::class)
    fun testUrlValidatorReturnsValidOnSortYoutubeUrlAndTimestamp() {
        val url = "https://youtu.be/j151FYZT4ZE?t=20"
        val youtubeUrlValidator = YoutubeUrlValidator()
        youtubeUrlValidator.checkVideoUrl(url)

        //Should not fail
    }

    @Test
    @Throws(InvalidVideoUrlException::class)
    fun testUrlValidatorReturnsValidOnYoutubeUrlAndTimestamp() {
        val url = "https://www.youtube.com/watch?v=j151FYZT4ZE&t=20"
        val youtubeUrlValidator = YoutubeUrlValidator()
        youtubeUrlValidator.checkVideoUrl(url)

        //Should not fail
    }

    @Test(expected = InvalidVideoUrlException::class)
    @Throws(InvalidVideoUrlException::class)
    fun testUrlValidatorThrowsExceptionOnInvalidUrl() {
        val url = "NoUrl"
        val youtubeUrlValidator = YoutubeUrlValidator()
        youtubeUrlValidator.checkVideoUrl(url)

        //Should fail
    }

    @Test
    @Throws(InvalidPlaylistUrlException::class)
    fun testUrlValidatorReturnsValidOnPlaylistUrl() {
        val url = "https://www.youtube.com/playlist?list=PL-_dGWmdQ0CJMcmQ5ximmrusq-0wjPbh8"
        val youtubeUrlValidator = YoutubeUrlValidator()
        youtubeUrlValidator.checkPlaylistUrl(url)

        //Should not fail
    }

    @Test(expected = InvalidPlaylistUrlException::class)
    @Throws(InvalidPlaylistUrlException::class)
    fun testUrlValidatorThrowsExceptionOnInvalidPlaylistUrl() {
        val url = "https://www.youtube.com/playlist?liPL-_dGWmdQ0CJMcmQ5ximmrusq-0wjPbh8"
        val youtubeUrlValidator = YoutubeUrlValidator()
        youtubeUrlValidator.checkPlaylistUrl(url)

        //Should fail
    }

    @Test(expected = InvalidPlaylistUrlException::class)
    @Throws(InvalidPlaylistUrlException::class)
    fun testUrlValidatorThrowsExceptionOnVideoUrl() {
        val url = "https://www.youtube.com/watch?v=j151FYZT4ZE"
        val youtubeUrlValidator = YoutubeUrlValidator()
        youtubeUrlValidator.checkPlaylistUrl(url)

        //Should fail
    }
}