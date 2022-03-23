package app.application.utils

import app.application.utils.YoutubeUrlValidator
import app.application.utils.LinkParameterBuilder
import kotlin.Throws
import app.application.exception.InvalidVideoUrlException
import java.net.MalformedURLException
import app.application.exception.InvalidPlaylistUrlException
import org.springframework.stereotype.Service
import java.net.URL

@Service
class YoutubeIdExtractor(private val youtubeUrlValidator: YoutubeUrlValidator, private val linkParameterBuilder: LinkParameterBuilder) {
    @Throws(InvalidVideoUrlException::class)
    fun getVideoIdFromLink(videoLink: String): String {
        return try {
            val url = URL(videoLink)
            val parameterMap = linkParameterBuilder.buildParameterMap(url.query)
            if (youtubeUrlValidator.isShortUrl(url.host)) url.path.substring(1) else parameterMap["v"]!!
        } catch (e: MalformedURLException) {
            throw InvalidVideoUrlException(e, videoLink)
        }
    }

    @Throws(InvalidPlaylistUrlException::class)
    fun getPlayListIdFromLink(playlistLink: String): String {
        return try {
            val url = URL(playlistLink)
            val parameterMap = linkParameterBuilder.buildParameterMap(url.query)
            parameterMap.getOrDefault("list", "")
        } catch (e: MalformedURLException) {
            throw InvalidPlaylistUrlException(e, playlistLink)
        }
    }
}