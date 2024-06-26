package app.application.spring.service.data

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
        return YoutubePlaylist(youtubeDownloader.getPlaylistInfo(requestPlaylistInfo).data())
    }
}