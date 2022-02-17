package app.application.data.entities;

import com.github.kiulian.downloader.model.playlist.PlaylistVideoDetails;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.Getter;

import java.util.Optional;

public class YoutubePlaylistVideoDetail extends YoutubeEntity<PlaylistVideoDetails>{

	@Getter
	private final BooleanProperty ignore;

	public YoutubePlaylistVideoDetail(PlaylistVideoDetails playlistVideoDetails) {
		this.reference = Optional.ofNullable(playlistVideoDetails);
		this.ignore = new SimpleBooleanProperty(false);
	}

	public String getVideoThumbnailUrl(){
		return getReference().thumbnails().get(0).split("\\?sqp")[0];
	}

	public String getVideoTitle(){
		return getReference().title();
	}

	public String getVideoId(){
		return getReference().videoId();
	}

}
