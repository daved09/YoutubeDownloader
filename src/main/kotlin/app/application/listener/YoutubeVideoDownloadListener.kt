package app.application.listener

import app.application.spring.service.DialogManager
import app.application.spring.service.GlobalObjectHandler
import javafx.application.Platform
import javafx.scene.control.Label
import javafx.scene.control.ProgressBar
import java.io.File

class YoutubeVideoDownloadListener(private val progressBar: ProgressBar,
                                   private val percentLabel: Label,
                                   dialogManager: DialogManager,
                                   private val globalObjectHandler: GlobalObjectHandler) : YoutubeDownloadListener(dialogManager) {
    private var currentProgress = 0
    override fun onFinished(data: File) {
        globalObjectHandler.hostServices?.showDocument(data.parent)
        Platform.runLater { dialogManager.openInformationDialog("Download finished.", "") }
    }

    override fun onDownloading(progress: Int) {
        currentProgress = progress
        Platform.runLater {
            progressBar.progress = progress.toString().toDouble() / 100
            percentLabel.text = "$progress%"
        }
    }

    override val isDownloadFinished: Boolean
        get() = currentProgress >= FINISHED_PROGRESS

    companion object {
        const val FINISHED_PROGRESS = 100
    }
}