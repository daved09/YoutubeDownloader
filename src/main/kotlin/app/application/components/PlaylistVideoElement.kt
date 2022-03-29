package app.application.components

import app.application.data.entities.YoutubeEntity
import app.application.data.entities.YoutubePlaylistVideoDetail
import javafx.scene.image.Image
import javafx.scene.input.MouseEvent

class PlaylistVideoElement(private val youtubePlaylistVideoDetail: YoutubePlaylistVideoDetail): VideoElement() {


    override fun loadVideoDetails() {
        imgThumbnail.image = Image(youtubePlaylistVideoDetail.videoThumbnailUrl)
        lblVideoTitle.text = youtubePlaylistVideoDetail.videoTitle
        chkIgnore.selectedProperty().bindBidirectional(youtubePlaylistVideoDetail.ignore)
    }

    override fun mouseClicked(event: MouseEvent) {
        if (event.clickCount != 2) {
            return
        }
        videoDetailsController.open()
        videoDetailsController.setVideoInfos(youtubePlaylistVideoDetail.videoId)
    }

}