package app.application.listener

import app.application.spring.service.DialogManager
import app.application.spring.service.GlobalObjectHandler
import javafx.application.Platform
import java.io.File

class YoutubePlaylistDownloadListener(dialogManager: DialogManager, private val videoAmount: Int, private val globalObjectHandler: GlobalObjectHandler) : YoutubeDownloadListener(dialogManager) {
    private var actualVideo = 0
    override fun onDownloading(progress: Int) {
        // Not used
    }

    override fun onFinished(data: File) {
        actualVideo++
        if (actualVideo == videoAmount) {
            globalObjectHandler.hostServices?.showDocument(data.parent)
            Platform.runLater { dialogManager.openInformationDialog("Download finished.", "") }
        }
    }

    override val isDownloadFinished: Boolean
        get() = false

}