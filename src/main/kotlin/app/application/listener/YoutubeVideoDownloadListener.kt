package app.application.listener

import app.application.utils.DialogManager
import app.application.utils.GlobalObjectHandler
import javafx.application.Platform
import javafx.scene.control.ProgressBar
import java.io.File

class YoutubeVideoDownloadListener(private val progressBar: ProgressBar, dialogManager: DialogManager, private val globalObjectHandler: GlobalObjectHandler) : YoutubeDownloadListener(dialogManager) {
    private var currentProgress = 0
    override fun onFinished(data: File) {
        globalObjectHandler.hostServices!!.showDocument(data.parent)
        Platform.runLater { dialogManager!!.openInformationDialog("Download fertig.", "") }
    }

    override fun onDownloading(progress: Int) {
        currentProgress = progress
        Platform.runLater { progressBar.progress = Integer.toString(progress).toDouble() / 100 }
    }

    override val isDownloadFinished: Boolean
        get() = currentProgress >= FINISHED_PROGRESS

    companion object {
        const val FINISHED_PROGRESS = 100
    }
}