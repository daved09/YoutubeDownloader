package app.application.data.entities

import com.github.kiulian.downloader.model.playlist.PlaylistVideoDetails
import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.scene.image.Image

class YoutubePlaylistVideoDetail(playlistVideoDetails: PlaylistVideoDetails?) : YoutubeEntity<PlaylistVideoDetails?>() {
    val ignore: BooleanProperty

    init {
        reference = playlistVideoDetails
        ignore = SimpleBooleanProperty(false)
    }

    private var _thumbnailImage: Image? = null

    val thumbnailImage: Image
    get() {
        if(_thumbnailImage == null){
            _thumbnailImage = Image(videoThumbnailUrl)
        }
        return _thumbnailImage as Image
    }

    private val videoThumbnailUrl: String?
        get() = reference?.thumbnails()?.get(0)?.split("\\?sqp")?.toTypedArray()?.get(0)

    val videoTitle: String?
        get() = reference?.title()

    val videoId: String?
        get() = reference?.videoId()
}