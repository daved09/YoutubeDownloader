package app.application.utils.service.data

import app.application.data.entities.YoutubePlaylist
import com.github.kiulian.downloader.YoutubeDownloader
import com.github.kiulian.downloader.downloader.request.RequestPlaylistInfo
import lombok.SneakyThrows
import org.springframework.stereotype.Service

@Service
class YoutubePlaylistDataService(youtubeDownloader: YoutubeDownloader) : YoutubeDataService(youtubeDownloader) {
    @SneakyThrows
    fun getPlaylistInfo(playListId: String?): YoutubePlaylist {
        val requestPlaylistInfo = RequestPlaylistInfo(playListId)
        val playlistInfo = youtubeDownloader!!.getPlaylistInfo(requestPlaylistInfo).data()
        return YoutubePlaylist(playlistInfo)
    }
}