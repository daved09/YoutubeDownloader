package app.application.controller.main_window

import app.application.data.entities.YoutubeVideo
import app.application.exception.CantAbortDownloadException
import app.application.exception.InvalidVideoUrlException
import app.application.listener.YoutubeVideoDownloadListener
import app.application.service.spring.*
import app.application.spring.service.*
import app.application.utils.*
import app.application.utils.DownloadExecutorHandler.DownloaderTask
import app.application.spring.service.data.YoutubeVideoDataService
import app.application.spring.service.download.YoutubeVideoDownloadService
import javafx.beans.binding.Bindings
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import org.springframework.stereotype.Component

@Component
class VideoPanelController(
    private val youtubeVideoDownloadService: YoutubeVideoDownloadService,
    private val youtubeVideoDataService: YoutubeVideoDataService,
    private val youtubeIdExtractor: YoutubeIdExtractor,
    private val youtubeUrlValidator: YoutubeUrlValidator,
    private val dialogManager: DialogManager,
    private val qualityLabelExtractor: QualityLabelExtractor,
    private val globalObjectHandler: GlobalObjectHandler
) {

    @FXML
    private lateinit var txtDownloadLink: TextField

    @FXML
    private lateinit var imgThumbnail: ImageView

    @FXML
    private lateinit var videoPane: AnchorPane

    @FXML
    private lateinit var lblVideoTitle: Label

    @FXML
    private lateinit var boxQuality: ComboBox<String>

    @FXML
    private lateinit var txtDescription: TextArea

    @FXML
    private lateinit var downloadProgress: ProgressBar

    @FXML
    private lateinit var chkAudioOnly: CheckBox

    @FXML
    private lateinit var btnSearch: Button

    private val downloadExecutorHandler: DownloadExecutorHandler = DownloadExecutorHandler()

    private var tmpYoutubeVideo: YoutubeVideo? = null

    @FXML
    private fun initialize() {
        youtubeVideoDownloadService.youtubeDownloadListener = YoutubeVideoDownloadListener(downloadProgress, dialogManager, globalObjectHandler)
        btnSearch.disableProperty().bind(Bindings.isEmpty(txtDownloadLink.textProperty()))
    }

    @Throws(InvalidVideoUrlException::class)
    fun btnSearchClick() {
        youtubeUrlValidator.checkVideoUrl(txtDownloadLink.text)
        tmpYoutubeVideo = youtubeVideoDataService.getYoutubeVideo(youtubeIdExtractor.getVideoIdFromLink(txtDownloadLink.text))
        updateGui(tmpYoutubeVideo!!)
        videoPane.isVisible = true
    }

    fun btnDownloadVideoClick() {
        downloadExecutorHandler.executeTask(object : DownloaderTask {
            override fun execute() {
                if (chkAudioOnly.isSelected) {
                    youtubeVideoDownloadService.downloadAudioOnlyAsync(tmpYoutubeVideo!!)
                } else {
                    youtubeVideoDownloadService.downloadVideoAsync(tmpYoutubeVideo!!, boxQuality.selectionModel.selectedItem)
                }
            }
        })
    }

    @Throws(CantAbortDownloadException::class)
    fun btnAbortClick() {
        downloadExecutorHandler.killTask()
    }

    private fun updateGui(youtubeVideo: YoutubeVideo) {
        imgThumbnail.image = Image(youtubeVideo.videoThumbnailUrl)
        lblVideoTitle.text = youtubeVideo.videoTitle
        refreshQualityBox(qualityLabelExtractor.getQualityLabels(youtubeVideo))
        txtDescription.text = youtubeVideo.videoDescription
    }

    private fun refreshQualityBox(listWithOptions: List<String>) {
        boxQuality.items.clear()
        boxQuality.items.addAll(listWithOptions)
        boxQuality.selectionModel.select(0)
    }
}