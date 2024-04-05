package app.application.controller.main_window

import app.application.components.VideoElement
import app.application.data.entities.YoutubePlaylist
import app.application.data.entities.YoutubePlaylistVideoDetail
import app.application.exception.CantAbortDownloadException
import app.application.exception.InvalidPlaylistUrlException
import app.application.spring.factories.VideoElementFactory
import app.application.utils.DownloadExecutorHandler
import app.application.utils.DownloadExecutorHandler.DownloaderTask
import app.application.spring.service.YoutubeIdExtractor
import app.application.spring.service.YoutubeUrlValidator
import app.application.spring.service.data.YoutubePlaylistDataService
import app.application.spring.service.download.YoutubePlaylistDownloadService
import javafx.beans.binding.Bindings
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.AnchorPane
import org.springframework.stereotype.Component
import java.util.function.Consumer

@Component
class PlaylistPanelController(
    private val youtubePlaylistDownloadService: YoutubePlaylistDownloadService,
    private val youtubePlaylistDataService: YoutubePlaylistDataService,
    private val youtubeIdExtractor: YoutubeIdExtractor,
    private val youtubeUrlValidator: YoutubeUrlValidator,
    private val videoElementFactory: VideoElementFactory
) {

    @FXML
    private lateinit var playlistPanel: AnchorPane

    @FXML
    private lateinit var txtPlaylistLink: TextField

    @FXML
    private lateinit var listPlaylist: ListView<VideoElement>

    @FXML
    private lateinit var txtPlaylistTitle: Label

    @FXML
    private lateinit var lblDownloadProgress: Label

    @FXML
    private lateinit var btnLoadPlaylist: Button

    @FXML
    private lateinit var chkAudioOnly: CheckBox

    private val downloadExecutorHandler: DownloadExecutorHandler = DownloadExecutorHandler()
    private var youtubePlaylist: YoutubePlaylist? = null

    @FXML
    fun initialize() {
        youtubePlaylistDownloadService.label = lblDownloadProgress
        btnLoadPlaylist.disableProperty().bind(Bindings.isEmpty(txtPlaylistLink.textProperty()))
    }

    @Throws(InvalidPlaylistUrlException::class)
    fun btnLoadPlaylistClick() {
        val url = txtPlaylistLink.text.trim()
        listPlaylist.items.clear()
        youtubeUrlValidator.checkPlaylistUrl(url)
        youtubePlaylist = youtubePlaylistDataService.getPlaylistInfo(youtubeIdExtractor.getPlayListIdFromLink(url))
        lblDownloadProgress.text = "Videos: 0/" + youtubePlaylist?.playlistSize
        txtPlaylistTitle.text = youtubePlaylist?.playlistTitle
        youtubePlaylist?.playlistVideos?.forEach(Consumer { video: YoutubePlaylistVideoDetail? -> listPlaylist.items.add(videoElementFactory.createVideoElement(video)) })
        chkAudioOnly.selectedProperty().bindBidirectional(youtubePlaylist?.audioOnly)
        playlistPanel.isVisible = true
    }

    fun btnPlaylistDownloadClick() {
        downloadExecutorHandler.executeTask(object : DownloaderTask {
            override fun execute() {
                youtubePlaylist?.let { youtubePlaylistDownloadService.downloadPlaylist(it) }
            }
        })
    }

    fun loadKeyPressed(keyEvent: KeyEvent){
        if(keyEvent.code == KeyCode.ENTER){
            btnLoadPlaylistClick()
        }
    }

    @Throws(CantAbortDownloadException::class)
    fun btnAbortClick() {
        downloadExecutorHandler.killTask()
    }
}