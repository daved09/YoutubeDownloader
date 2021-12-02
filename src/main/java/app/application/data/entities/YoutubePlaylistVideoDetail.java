package app.application.data.entities;

import com.github.kiulian.downloader.model.playlist.PlaylistVideoDetails;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.Getter;

public class YoutubePlaylistVideoDetail extends YoutubeEntity<PlaylistVideoDetails>{

	@Getter
	private final BooleanProperty ignore;

	public YoutubePlaylistVideoDetail(PlaylistVideoDetails playlistVideoDetails) {
		this.reference = playlistVideoDetails;
		this.ignore = new SimpleBooleanProperty(false);
	}

	public String getVideoThumbnailUrl(){
		return reference.thumbnails().get(0).split("\\?sqp")[0];
	}

	public String getVideoTitle(){
		return reference.title();
	}

	public String getVideoId(){
		return reference.videoId();
	}

}
