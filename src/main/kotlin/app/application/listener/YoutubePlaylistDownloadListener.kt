package app.application.listener

import app.application.utils.DialogManager
import app.application.utils.GlobalObjectHandler
import javafx.application.Platform
import java.io.File

class YoutubePlaylistDownloadListener(dialogManager: DialogManager, private val videoAmount: Int, private val globalObjectHandler: GlobalObjectHandler) : YoutubeDownloadListener(dialogManager) {
    private var actualVideo = 0
    override fun onDownloading(progress: Int) {
        // Wird nicht verwendet
    }

    override fun onFinished(data: File) {
        actualVideo++
        if (actualVideo == videoAmount) {
            globalObjectHandler.hostServices!!.showDocument(data.parent)
            Platform.runLater { dialogManager!!.openInformationDialog("Download fertig.", "") }
        }
    }

    override val isDownloadFinished: Boolean
        get() = false

}