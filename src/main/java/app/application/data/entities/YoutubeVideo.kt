package app.application.data.entities

import com.github.kiulian.downloader.model.videos.VideoDetails
import com.github.kiulian.downloader.model.videos.VideoInfo
import com.github.kiulian.downloader.model.videos.formats.AudioFormat
import com.github.kiulian.downloader.model.videos.formats.Format
import com.github.kiulian.downloader.model.videos.formats.VideoWithAudioFormat

class YoutubeVideo(videoInfo: VideoInfo?) : YoutubeEntity<VideoInfo?>() {
    init {
        reference = videoInfo
    }

    val videoId: String
        get() = videoDetails.videoId()
    val videoDescription: String
        get() = videoDetails.description()
    val videoTitle: String
        get() = videoDetails.title()
    val videoThumbnailUrl: String
        get() = videoDetails.thumbnails()[0].split("\\?sqp").toTypedArray()[0]
    val videoDetails: VideoDetails
        get() = reference!!.details()
    val videoWithAudioFormat: List<VideoWithAudioFormat>
        get() = reference!!.videoWithAudioFormats()
    val audioFormat: AudioFormat
        get() = reference!!.audioFormats()[0]
    val bestVideoWithAudioFormat: Format
        get() = reference!!.bestVideoWithAudioFormat()
}