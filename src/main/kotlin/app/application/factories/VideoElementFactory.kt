package app.application.factories

import app.application.components.PlaylistVideoElement
import app.application.components.VideoElement
import app.application.components.YoutubeVideoElement
import app.application.data.entities.YoutubePlaylistVideoDetail
import app.application.data.entities.YoutubeVideo
import org.springframework.beans.factory.config.AutowireCapableBeanFactory
import org.springframework.stereotype.Service

@Service
class VideoElementFactory(private val autowireCapableBeanFactory: AutowireCapableBeanFactory) {

    fun createVideoElement(youtubePlaylistVideoDetail: YoutubePlaylistVideoDetail?): VideoElement? {
        val videoElement = youtubePlaylistVideoDetail?.let { PlaylistVideoElement(it) }
        autowireCapableBeanFactory.autowireBean(videoElement)
        videoElement?.loadVideoDetails()
        return videoElement
    }

    fun createVideoElement(youtubeVideo: YoutubeVideo?): VideoElement? {
        val videoElement = youtubeVideo?.let { YoutubeVideoElement(it) }
        autowireCapableBeanFactory.autowireBean(videoElement)
        videoElement?.loadVideoDetails()
        return videoElement
    }

}