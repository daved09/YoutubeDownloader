package app.application.factories

import app.application.components.VideoElement
import app.application.data.entities.YoutubePlaylistVideoDetail
import org.springframework.beans.factory.config.AutowireCapableBeanFactory
import org.springframework.stereotype.Service

@Service
class VideoElementFactory(private val autowireCapableBeanFactory: AutowireCapableBeanFactory) {

    fun createVideoElement(youtubePlaylistVideoDetail: YoutubePlaylistVideoDetail?): VideoElement {
        val videoElement = VideoElement()
        autowireCapableBeanFactory.autowireBean(videoElement)
        videoElement.loadVideoDetails(youtubePlaylistVideoDetail!!)
        return videoElement
    }

}