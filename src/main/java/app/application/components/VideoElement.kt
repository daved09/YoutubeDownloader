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
    private val imgThumbnail: ImageView? = null

    @FXML
    private val lblVideoTitle: Label? = null

    @FXML
    private val chkIgnore: CheckBox? = null

    @Autowired
    private val videoDetailsController: VideoDetailsController? = null
    private var youtubePlaylistVideoDetail: YoutubePlaylistVideoDetail? = null

    init {
        loadComponent(this)
    }

    fun loadVideoDetails(youtubePlaylistVideoDetail: YoutubePlaylistVideoDetail) {
        this.youtubePlaylistVideoDetail = youtubePlaylistVideoDetail
        imgThumbnail!!.image = Image(youtubePlaylistVideoDetail.videoThumbnailUrl)
        lblVideoTitle!!.text = youtubePlaylistVideoDetail.videoTitle
        chkIgnore!!.selectedProperty().bindBidirectional(youtubePlaylistVideoDetail.ignore)
    }

    fun mouseClicked(event: MouseEvent) {
        if (event.clickCount != 2) {
            return
        }
        videoDetailsController!!.open()
        videoDetailsController.setVideoInfos(youtubePlaylistVideoDetail!!.videoId)
    }
}