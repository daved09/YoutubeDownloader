package app.application.data.entities

import com.github.kiulian.downloader.model.playlist.PlaylistInfo
import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleBooleanProperty

class YoutubePlaylist(playlistInfo: PlaylistInfo?) : YoutubeEntity<PlaylistInfo?>() {
    private val youtubePlaylistVideoDetailsCache: MutableList<YoutubePlaylistVideoDetail> = ArrayList()

    var audioOnly: BooleanProperty

    init {
        reference = playlistInfo
        audioOnly = SimpleBooleanProperty(false)
    }

    val playlistSize: Int
        get() = reference?.details()?.videoCount() ?: 0

    val playlistVideos: List<YoutubePlaylistVideoDetail>
        get() {
            if (youtubePlaylistVideoDetailsCache.isEmpty()) {
                reference?.also {
                    for (video in it.videos()) {
                        youtubePlaylistVideoDetailsCache.add(YoutubePlaylistVideoDetail(video))
                    }
                }
            }
            return youtubePlaylistVideoDetailsCache
        }

    val playlistTitle: String
        get() = reference?.details()?.title() ?: "No Playlist"
}