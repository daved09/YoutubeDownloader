package app.application.spring.service.download

import app.application.data.entities.YoutubeVideo
import app.application.data.entities.YoutubeVideoSettingsEntity
import com.github.kiulian.downloader.downloader.request.RequestVideoFileDownload
import com.github.kiulian.downloader.model.videos.formats.AudioFormat
import com.github.kiulian.downloader.model.videos.formats.Format
import lombok.SneakyThrows
import org.springframework.stereotype.Service
import java.nio.file.Paths

@Service
class YoutubeVideoDownloadService : YoutubeDownloadService() {
    @SneakyThrows
    fun downloadVideoAsync(youtubeVideoSettingsEntity: YoutubeVideoSettingsEntity) {
        downloadAsync(
            youtubeVideoSettingsEntity.youtubeEntity!!,
            if (youtubeVideoSettingsEntity.settingsEntity?.audioOnlyProperty?.get()!!)
                getAudioFormat(youtubeVideoSettingsEntity) else youtubeVideoSettingsEntity.settingsEntity?.qualityProperty?.get())
    }

    @SneakyThrows
    protected fun downloadAsync(youtubeVideo: YoutubeVideo, format: Format?) {
        val requestVideoFileDownload = RequestVideoFileDownload(format)
        requestVideoFileDownload.callback(youtubeDownloadListener)
                .renameTo(youtubeVideo.videoTitle)
                .overwriteIfExists(userConfigHandler.userConfig!!.overwriteExistingVideo.get())
                .saveTo(Paths.get(userConfigHandler.userConfig!!.downloadDir.get()).toFile())
        youtubeDownloader.downloadVideoFile(requestVideoFileDownload).data()
    }

    private fun getAudioFormat(youtubeVideoSettingsEntity: YoutubeVideoSettingsEntity): AudioFormat {
        return youtubeVideoSettingsEntity.youtubeEntity!!.audioFormat
    }


}