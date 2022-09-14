package app.application.spring.service.download

import app.application.data.entities.YoutubeVideo
import app.application.data.entities.YoutubeVideoSettingsEntity
import app.application.exception.CantDeleteFileException
import com.github.kiulian.downloader.downloader.request.RequestVideoFileDownload
import com.github.kiulian.downloader.model.videos.formats.Format
import com.github.kiulian.downloader.model.videos.formats.VideoWithAudioFormat
import lombok.SneakyThrows
import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Paths

@Service
class YoutubeVideoDownloadService : YoutubeDownloadService() {
    @SneakyThrows
    fun downloadVideoAsync(youtubeVideoSettingsEntity: YoutubeVideoSettingsEntity) {
        downloadAsync(
            youtubeVideoSettingsEntity.youtubeEntity!!,
            youtubeVideoSettingsEntity.settingsEntity?.qualityProperty?.get(),
        youtubeVideoSettingsEntity.settingsEntity?.audioOnlyProperty!!.get())
    }

    @SneakyThrows
    protected fun downloadAsync(youtubeVideo: YoutubeVideo, format: Format?, audioOnly: Boolean) {
        val requestVideoFileDownload = RequestVideoFileDownload(format)
        requestVideoFileDownload.callback(youtubeDownloadListener)
                .renameTo(youtubeVideo.videoTitle)
                .overwriteIfExists(userConfigHandler.userConfig!!.overwriteExistingVideo.get())
                .saveTo(Paths.get(userConfigHandler.userConfig!!.downloadDir.get()).toFile())
        val videoFile = youtubeDownloader.downloadVideoFile(requestVideoFileDownload).data()
        if(audioOnly){
            youtubeVideoConverter.convert(videoFile)
            deleteOldVideoFile(videoFile)
        }
    }

    private fun deleteOldVideoFile(videoFile: File){
        val success = videoFile.delete()
        if(!success){
            throw CantDeleteFileException("File ${videoFile.absoluteFile} couldn´t be deleted.")
        }
    }

}