package app.application.data.entities

import com.github.kiulian.downloader.model.videos.VideoDetails
import com.github.kiulian.downloader.model.videos.VideoInfo
import com.github.kiulian.downloader.model.videos.formats.AudioFormat
import com.github.kiulian.downloader.model.videos.formats.Format
import com.github.kiulian.downloader.model.videos.formats.VideoFormat
import com.github.kiulian.downloader.model.videos.formats.VideoWithAudioFormat
import javafx.scene.image.Image

class YoutubeVideo(videoInfo: VideoInfo?) : YoutubeEntity<VideoInfo?>() {
    init {
        reference = videoInfo
    }

    private var _thumbnailImage: Image? = null
    val thumbnailImage: Image
    get() {
        if(_thumbnailImage == null){
            _thumbnailImage = Image(videoThumbnailUrl)
        }
        return _thumbnailImage as Image
    }
    val videoId: String?
        get() = videoDetails?.videoId()
    val videoDescription: String?
        get() = videoDetails?.description()
    val videoTitle: String?
        get() = videoDetails?.title()
    val videoThumbnailUrl: String?
        get() = videoDetails?.thumbnails()?.get(0)?.split("\\?sqp")?.toTypedArray()?.get(0)
    private val videoDetails: VideoDetails?
        get() = reference?.details()
    val videoWithAudioFormat: MutableList<VideoWithAudioFormat>?
        get() = reference?.videoWithAudioFormats()
    val audioFormat: AudioFormat?
        get() = reference?.audioFormats()?.get(0)
    val bestVideoWithAudioFormat: VideoFormat?
        get() = reference?.bestVideoWithAudioFormat()
}