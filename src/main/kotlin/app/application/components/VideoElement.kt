package app.application.components

import app.application.controller.VideoDetailsController
import app.application.data.entities.YoutubePlaylistVideoDetail
import app.application.utils.ComponentUtils.Companion.loadComponent
import javafx.fxml.FXML
import javafx.scene.control.CheckBox
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.AnchorPane
import org.springframework.beans.factory.annotation.Autowired

class VideoElement : AnchorPane() {
    @FXML
    private lateinit var imgThumbnail: ImageView

    @FXML
    private lateinit var lblVideoTitle: Label

    @FXML
    private lateinit var chkIgnore: CheckBox

    @Autowired
    private lateinit var videoDetailsController: VideoDetailsController

    private var youtubePlaylistVideoDetail: YoutubePlaylistVideoDetail? = null

    init {
        loadComponent(this)
    }

    fun loadVideoDetails(youtubePlaylistVideoDetail: YoutubePlaylistVideoDetail) {
        this.youtubePlaylistVideoDetail = youtubePlaylistVideoDetail
        imgThumbnail.image = youtubePlaylistVideoDetail.thumbnailImage
        lblVideoTitle.text = youtubePlaylistVideoDetail.videoTitle
        chkIgnore.selectedProperty().bindBidirectional(youtubePlaylistVideoDetail.ignore)
    }

    fun mouseClicked(event: MouseEvent) {
        if (event.clickCount != 2) {
            return
        }
        videoDetailsController.open()
        videoDetailsController.setVideoInfos(youtubePlaylistVideoDetail!!.videoId)
    }
}