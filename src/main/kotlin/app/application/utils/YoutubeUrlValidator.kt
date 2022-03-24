package app.application.utils

import kotlin.Throws
import app.application.exception.InvalidVideoUrlException
import app.application.exception.InvalidPlaylistUrlException
import app.application.utils.GlobalValues
import org.apache.commons.validator.routines.UrlValidator
import org.springframework.stereotype.Service

@Service
class YoutubeUrlValidator {

    private val urlValidator = UrlValidator()

    @Throws(InvalidVideoUrlException::class)
    fun checkVideoUrl(url: String) {
        if (!urlValidator.isValid(url) || !isYoutubeUrl(url) || !hasVideoID(url)) {
            throw InvalidVideoUrlException(url)
        }
    }

    @Throws(InvalidPlaylistUrlException::class)
    fun checkPlaylistUrl(url: String) {
        if (!urlValidator.isValid(url) || !hasPlaylistID(url)) {
            throw InvalidPlaylistUrlException(url)
        }
    }

    fun isShortUrl(url: String): Boolean {
        return url.contains(GlobalValues.YOUTUBE_SHORT_URL)
    }

    private fun isYoutubeUrl(url: String): Boolean {
        return url.contains(GlobalValues.YOUTUBE_URL) || isShortUrl(url)
    }

    private fun hasVideoID(url: String): Boolean {
        return url.contains("v=") || isShortUrl(url)
    }

    private fun hasPlaylistID(url: String): Boolean {
        return url.contains("list=")
    }
}