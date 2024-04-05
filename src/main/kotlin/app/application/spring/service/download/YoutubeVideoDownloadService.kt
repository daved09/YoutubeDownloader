package app.application.spring.service.download

import app.application.data.entities.YoutubeVideo
import app.application.data.entities.YoutubeVideoSettingsEntity
import app.application.exception.CantDeleteFileException
import com.github.kiulian.downloader.downloader.request.RequestVideoFileDownload
import com.github.kiulian.downloader.model.videos.formats.AudioFormat
import com.github.kiulian.downloader.model.videos.formats.Format
import lombok.SneakyThrows
import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Paths

@Service
class YoutubeVideoDownloadService : YoutubeDownloadService() {
    @SneakyThrows
    fun downloadVideoAsync(youtubeVideoSettingsEntity: YoutubeVideoSettingsEntity) {
        downloadAsync(youtubeVideoSettingsEntity)
    }

    @SneakyThrows
    protected fun downloadAsync(youtubeVideoSettingsEntity: YoutubeVideoSettingsEntity) {
        val requestVideoFileDownload = RequestVideoFileDownload(youtubeVideoSettingsEntity.settingsEntity?.qualityProperty?.get())
        requestVideoFileDownload.callback(youtubeDownloadListener)
                .renameTo(youtubeVideoSettingsEntity.youtubeEntity?.videoTitle)
                .overwriteIfExists(userConfigHandler.userConfig.overwriteExistingVideo.get())
                .saveTo(Paths.get(userConfigHandler.userConfig.downloadDir.get()).toFile())
        val videoFile = youtubeDownloader.downloadVideoFile(requestVideoFileDownload).data()
        if(youtubeVideoSettingsEntity.settingsEntity?.audioOnlyProperty?.get() as Boolean){
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