package app.application.utils.service.data

import app.application.data.entities.YoutubeVideo
import com.github.kiulian.downloader.YoutubeDownloader
import com.github.kiulian.downloader.downloader.request.RequestVideoInfo
import org.springframework.stereotype.Service

@Service
class YoutubeVideoDataService(youtubeDownloader: YoutubeDownloader) : YoutubeDataService(youtubeDownloader) {
    fun getYoutubeVideo(videoId: String?): YoutubeVideo {
        val requestVideoInfo = RequestVideoInfo(videoId)
        return YoutubeVideo(youtubeDownloader!!.getVideoInfo(requestVideoInfo).data())
    }
}