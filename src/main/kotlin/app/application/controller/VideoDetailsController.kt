package app.application.controller

import app.application.spring.service.GlobalObjectHandler
import app.application.spring.service.data.YoutubeVideoDataService
import javafx.fxml.FXML
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Hyperlink
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.control.Tooltip
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent
import javafx.stage.Stage
import net.rgielen.fxweaver.core.FxWeaver
import net.rgielen.fxweaver.core.FxmlView
import org.springframework.stereotype.Component

@Component
@FxmlView("/views/VideoDetails.fxml")
class VideoDetailsController(
    private val fxWeaver: FxWeaver,
    private val youtubeVideoDataService: YoutubeVideoDataService,
    private val globalObjectHandler: GlobalObjectHandler
) {
    
    @FXML
    private lateinit var imgThumbnail: ImageView

    @FXML
    private lateinit var txtVideoDescription: TextArea

    @FXML
    private lateinit var lblVideoTitle: Label

    @FXML
    private lateinit var videoLink: Hyperlink
    
    
    fun setVideoInfos(videoId: String) {
        val youtubeVideo = youtubeVideoDataService.getYoutubeVideo(videoId)
        imgThumbnail.image = Image(youtubeVideo.videoThumbnailUrl)
        txtVideoDescription.text = youtubeVideo.videoDescription
        lblVideoTitle.text = youtubeVideo.videoTitle
        videoLink.text = "https://youtube.com/watch?v=$videoId"
    }

    fun open() {
        val stage = Stage()
        val root = fxWeaver.loadView<VideoDetailsController, Parent>(VideoDetailsController::class.java)
        val scene = Scene(root)
        stage.title = "Video details"
        stage.scene = scene
        stage.show()
    }

    fun showInBrowser() {
        globalObjectHandler.hostServices?.showDocument(videoLink.text)
    }

    fun copyToClipboard() {
        val clipboard = Clipboard.getSystemClipboard()
        val content = ClipboardContent()
        content.putString(videoLink.text)
        clipboard.setContent(content)
        val tooltip = Tooltip()
        tooltip.text = "Copied"
        tooltip.show(videoLink.scene.window)
    }
}