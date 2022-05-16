package app.application.listener

import app.application.spring.service.DialogManager
import com.github.kiulian.downloader.downloader.YoutubeProgressCallback
import lombok.AllArgsConstructor
import java.io.File

@AllArgsConstructor
abstract class YoutubeDownloadListener(val dialogManager: DialogManager) : YoutubeProgressCallback<File> {
    override fun onError(throwable: Throwable) {}
    abstract val isDownloadFinished: Boolean
}