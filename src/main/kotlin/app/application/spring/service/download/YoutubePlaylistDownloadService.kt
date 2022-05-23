package app.application.spring.service.download

import app.application.data.entities.YoutubePlaylist
import app.application.data.entities.YoutubePlaylistVideoDetail
import app.application.data.entities.YoutubeVideo
import app.application.exception.CantDeleteFileException
import app.application.listener.YoutubePlaylistDownloadListener
import app.application.spring.service.GlobalObjectHandler
import app.application.spring.service.data.YoutubeVideoDataService
import com.github.kiulian.downloader.downloader.request.RequestVideoFileDownload
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
        val requestVideoFileDownload = RequestVideoFileDownload(youtubeVideo.bestVideoWithAudioFormat)
        requestVideoFileDownload.renameTo(youtubeVideo.videoTitle).overwriteIfExists(true)
                .saveTo(Paths.get(
                    userConfigHandler.userConfig!!.downloadDir.get() + File.separator +
                        if (userConfigHandler.userConfig!!.subFolderForPlaylists.get()) youtubePlaylist.playlistTitle else "").toFile())
                .callback(youtubeDownloadListener)
        val videoFile = youtubeDownloader.downloadVideoFile(requestVideoFileDownload).data()
        youtubeVideoConverter.convert(videoFile)
        deleteOldVideoFile(videoFile)
    }

    private fun deleteOldVideoFile(videoFile: File){
        val success = videoFile.delete()
        if(!success){
            throw CantDeleteFileException("File ${videoFile.absoluteFile} couldn´t be deleted.")
        }
    }

    private fun setLabelProgress(current: Int, max: Int) {
        Platform.runLater { label!!.text = "Videos: $current/$max" }
    }
}