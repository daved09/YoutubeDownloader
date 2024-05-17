package app.application.controller.main_window

import app.application.data.entities.*
import app.application.exception.CantAbortDownloadException
import app.application.exception.InvalidVideoUrlException
import app.application.listener.YoutubeVideoDownloadListener
import app.application.spring.service.*
import app.application.spring.service.data.YoutubeVideoDataService
import app.application.spring.service.download.YoutubeVideoDownloadService
import app.application.utils.*
import app.application.utils.DownloadExecutorHandler.DownloaderTask
import com.github.kiulian.downloader.model.videos.formats.VideoWithAudioFormat
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.image.ImageView
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.AnchorPane
import javafx.util.Callback
import org.springframework.stereotype.Component

@Component
class VideoPanelController(
    private val youtubeVideoDownloadService: YoutubeVideoDownloadService,
    private val youtubeVideoDataService: YoutubeVideoDataService,
    private val youtubeIdExtractor: YoutubeIdExtractor,
    private val youtubeUrlValidator: YoutubeUrlValidator,
    private val dialogManager: DialogManager,
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
    private lateinit var boxQuality: ComboBox<VideoWithAudioFormat>

    @FXML
    private lateinit var txtDescription: TextArea

    @FXML
    private lateinit var downloadProgress: ProgressBar

    @FXML
    private lateinit var chkAudioOnly: CheckBox

    @FXML
    private lateinit var btnSearch: Button

    @FXML
    private lateinit var lblPercent: Label

    private val downloadExecutorHandler: DownloadExecutorHandler = DownloadExecutorHandler()

    private var actualYoutubeSettingsEntity: YoutubeVideoSettingsEntity? = null

    @FXML
    private fun initialize() {
        youtubeVideoDownloadService.youtubeDownloadListener = YoutubeVideoDownloadListener(downloadProgress, lblPercent, dialogManager, globalObjectHandler)
        btnSearch.disableProperty().bind(Bindings.isEmpty(txtDownloadLink.textProperty()))
        setupQualityBoxRendering()
    }

    private fun setupQualityBoxRendering() {
        boxQuality.cellFactory = Callback<ListView<VideoWithAudioFormat>, ListCell<VideoWithAudioFormat>> {
            QualityLabelListCell()
        }
        boxQuality.buttonCell = QualityLabelListCell()
    }

    @Throws(InvalidVideoUrlException::class)
    fun btnSearchClick() {
        val url = txtDownloadLink.text.trim()
        youtubeUrlValidator.checkVideoUrl(url)
        actualYoutubeSettingsEntity = YoutubeVideoSettingsEntity(youtubeVideoDataService.getYoutubeVideo(youtubeIdExtractor.getVideoIdFromLink(url)))
        updateGui()
        videoPane.isVisible = true
    }

    fun btnDownloadVideoClick() {
        downloadExecutorHandler.executeTask(object : DownloaderTask {
            override fun execute() {
                youtubeVideoDownloadService.downloadVideoAsync(actualYoutubeSettingsEntity!!)
            }
        })
    }

    @Throws(CantAbortDownloadException::class)
    fun btnAbortClick() {
        downloadExecutorHandler.killTask()
    }

    fun searchKeyPressed(keyEvent: KeyEvent){
        if(keyEvent.code == KeyCode.ENTER && !btnSearch.isDisabled){
            btnSearchClick()
        }
    }

    private fun updateGui() {
        imgThumbnail.image = actualYoutubeSettingsEntity?.youtubeEntity?.thumbnailImage
        lblVideoTitle.text = actualYoutubeSettingsEntity?.youtubeEntity?.videoTitle
        refreshQualityBox(actualYoutubeSettingsEntity?.youtubeEntity?.videoWithAudioFormat)
        txtDescription.text = actualYoutubeSettingsEntity?.youtubeEntity?.videoDescription
        actualYoutubeSettingsEntity?.settingsEntity = SettingsEntity(SimpleObjectProperty(actualYoutubeSettingsEntity?.youtubeEntity?.videoWithAudioFormat?.get(0)), SimpleBooleanProperty(chkAudioOnly.isSelected))
        actualYoutubeSettingsEntity?.settingsEntity?.qualityProperty?.bind(boxQuality.valueProperty())
        actualYoutubeSettingsEntity?.settingsEntity?.audioOnlyProperty?.bind(chkAudioOnly.selectedProperty())
    }

    private fun refreshQualityBox(listWithOptions: List<VideoWithAudioFormat>?) {
        boxQuality.items.clear()
        listWithOptions?.let { boxQuality.items.addAll(it) }
        boxQuality.selectionModel.select(0)
    }
}