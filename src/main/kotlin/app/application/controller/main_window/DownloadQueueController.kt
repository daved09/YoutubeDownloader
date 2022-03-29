package app.application.controller.main_window

import app.application.components.VideoElement
import app.application.data.entities.YoutubeVideo
import app.application.data.model.QueueManager
import app.application.data.model.QueueManager.QueueListener
import app.application.factories.VideoElementFactory
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.control.ListView
import org.springframework.stereotype.Component


@Component
class DownloadQueueController(private val queueManager: QueueManager,
                private val videoElementFactory: VideoElementFactory) {

    @FXML
    private lateinit var listQueue: ListView<VideoElement>

    @FXML
    private lateinit var lblQueueSize: Label

    @FXML
    fun initialize(){
        queueManager.queueListener = object : QueueListener {
            override fun queueChanged(youtubeVideo: YoutubeVideo) {
                lblQueueSize.text = queueManager.downloadQueue.getDownloadList().size.toString()
                listQueue.items.add(videoElementFactory.createVideoElement(youtubeVideo))
            }
        }
    }

    fun btnQueueClearClick(){
//        queueManager.downloadQueue.getDownloadListAndClear()
    }

}