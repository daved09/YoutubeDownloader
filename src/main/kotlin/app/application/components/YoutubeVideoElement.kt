package app.application.components

import app.application.data.entities.YoutubeVideo
import javafx.scene.image.Image
import javafx.scene.input.MouseEvent

class YoutubeVideoElement(private val youtubeVideo: YoutubeVideo): VideoElement() {


    override fun loadVideoDetails() {
        imgThumbnail.image = Image(youtubeVideo.videoThumbnailUrl)
        lblVideoTitle.text = youtubeVideo.videoTitle
    }

    override fun mouseClicked(event: MouseEvent) {
        //
    }
}