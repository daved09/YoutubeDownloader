package app.application.data.entities;

import com.github.kiulian.downloader.model.playlist.PlaylistInfo;
import com.github.kiulian.downloader.model.playlist.PlaylistVideoDetails;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class YoutubePlaylist extends YoutubeEntity<PlaylistInfo> {

	private final List<YoutubePlaylistVideoDetail> youtubePlaylistVideoDetailsCache = new ArrayList<>();

	@Getter
	private final BooleanProperty audioOnly;

	public YoutubePlaylist(PlaylistInfo playlistInfo){
		this.reference = Optional.ofNullable(playlistInfo);
		this.audioOnly = new SimpleBooleanProperty(false);
	}

	public int getPlaylistSize(){
		return getReference().details().videoCount();
	}

	public List<YoutubePlaylistVideoDetail> getPlaylistVideos(){
		if(youtubePlaylistVideoDetailsCache.isEmpty()){
			for (PlaylistVideoDetails video : getReference().videos()) {
				youtubePlaylistVideoDetailsCache.add(new YoutubePlaylistVideoDetail(video));
			}
		}
		return youtubePlaylistVideoDetailsCache;
	}

	public String getPlaylistTitle() {
		return getReference().details().title();
	}


}
