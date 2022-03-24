package app.application.utils.service.download

import app.application.data.entities.YoutubeVideo
import com.github.kiulian.downloader.downloader.request.RequestVideoFileDownload
import com.github.kiulian.downloader.model.videos.formats.Format
import com.github.kiulian.downloader.model.videos.formats.VideoWithAudioFormat
import lombok.SneakyThrows
import org.springframework.stereotype.Service
import java.nio.file.Paths

@Service
class YoutubeVideoDownloadService : YoutubeDownloadService() {
    @SneakyThrows
    fun downloadVideoAsync(youtubeVideo: YoutubeVideo, quality: String) {
        downloadAsync(youtubeVideo, selectAudioVideoFormat(youtubeVideo, quality))
    }

    fun downloadAudioOnlyAsync(youtubeVideo: YoutubeVideo) {
        downloadAsync(youtubeVideo, youtubeVideo.audioFormat)
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

    private fun selectAudioVideoFormat(youtubeVideo: YoutubeVideo, quality: String): VideoWithAudioFormat {
        return youtubeVideo.videoWithAudioFormat.stream().filter{ audioVideoFormats: VideoWithAudioFormat -> audioVideoFormats.qualityLabel() == quality }.findFirst().get()
    }
}