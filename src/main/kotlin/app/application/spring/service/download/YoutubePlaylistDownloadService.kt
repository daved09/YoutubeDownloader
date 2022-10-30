package app.application.spring.service.download

import app.application.data.entities.YoutubePlaylist
import app.application.data.entities.YoutubePlaylistVideoDetail
import app.application.data.entities.YoutubeVideo
import app.application.exception.CantDeleteFileException
import app.application.listener.YoutubePlaylistDownloadListener
import app.application.spring.service.GlobalObjectHandler
import app.application.spring.service.data.YoutubeVideoDataService
import com.github.kiulian.downloader.downloader.request.RequestVideoFileDownload
import com.github.kiulian.downloader.model.videos.formats.Format
import javafx.application.Platform
import javafx.scene.control.Label
import lombok.SneakyThrows
import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Paths
import java.util.concurrent.atomic.AtomicInteger

@Service
class YoutubePlaylistDownloadService(private val youtubeVideoDataService: YoutubeVideoDataService, private val globalObjectHandler: GlobalObjectHandler) : YoutubeDownloadService() {
    var label: Label? = null

    @SneakyThrows
    fun downloadPlaylist(youtubePlaylist: YoutubePlaylist) {
        youtubeDownloadListener = YoutubePlaylistDownloadListener(dialogManager, youtubePlaylist.playlistSize, globalObjectHandler)
        val size = youtubePlaylist.playlistSize
        val progress = AtomicInteger(0)
        youtubePlaylist.playlistVideos.stream().filter { video: YoutubePlaylistVideoDetail -> !video.ignore.get() }
                .forEach { video: YoutubePlaylistVideoDetail -> downloadVideo(youtubePlaylist, size, progress, video) }
    }

    private fun downloadVideo(
            youtubePlaylist: YoutubePlaylist,
            size: Int,
            progress: AtomicInteger,
            video: YoutubePlaylistVideoDetail) {
        progress.getAndIncrement()
        setLabelProgress(progress.get(), size)
        downloadAsync(youtubePlaylist, youtubeVideoDataService.getYoutubeVideo(video.videoId))
    }

    protected fun downloadAsync(youtubePlaylist: YoutubePlaylist, youtubeVideo: YoutubeVideo) {
        val requestVideoFileDownload = RequestVideoFileDownload(getAudioOrVideoFormat(youtubePlaylist, youtubeVideo))
        requestVideoFileDownload.renameTo(youtubeVideo.videoTitle).overwriteIfExists(true)
                .saveTo(Paths.get(
                    userConfigHandler.userConfig!!.downloadDir.get() + File.separator +
                        if (userConfigHandler.userConfig!!.subFolderForPlaylists.get()) youtubePlaylist.playlistTitle else "").toFile())
                .callback(youtubeDownloadListener)
        youtubeDownloader.downloadVideoFile(requestVideoFileDownload).data()
    }

    private fun getAudioOrVideoFormat(youtubePlaylist: YoutubePlaylist, youtubeVideo: YoutubeVideo): Format {
        return if (youtubePlaylist.audioOnly.get()) {
            youtubeVideo.audioFormat
        } else youtubeVideo.bestVideoWithAudioFormat
    }

    private fun setLabelProgress(current: Int, max: Int) {
        Platform.runLater { label!!.text = "Videos: $current/$max" }
    }
}