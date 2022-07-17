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

    var _thumbnailImage: Image? = null

    val thumbnailImage: Image
    get() {
        if(_thumbnailImage == null){
            _thumbnailImage = Image(videoThumbnailUrl)
        }
        return _thumbnailImage!!
    }

    val videoThumbnailUrl: String
        get() = reference!!.thumbnails()[0].split("\\?sqp").toTypedArray()[0]

    val videoTitle: String
        get() = reference!!.title()

    val videoId: String
        get() = reference!!.videoId()
}