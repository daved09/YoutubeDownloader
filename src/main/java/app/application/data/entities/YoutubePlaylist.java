package app.application.data.entities;

import com.github.kiulian.downloader.model.playlist.PlaylistInfo;
import com.github.kiulian.downloader.model.playlist.PlaylistVideoDetails;

import java.util.ArrayList;
import java.util.List;

public class YoutubePlaylist extends YoutubeEntity<PlaylistInfo> {

	private final List<YoutubePlaylistVideoDetail> youtubePlaylistVideoDetailsCache = new ArrayList<>();

	public YoutubePlaylist(PlaylistInfo playlistInfo){
		this.reference = playlistInfo;
	}

	public int getPlaylistSize(){
		return reference.details().videoCount();
	}

	public List<YoutubePlaylistVideoDetail> getPlaylistVideos(){
		if(youtubePlaylistVideoDetailsCache.isEmpty()){
			for (PlaylistVideoDetails video : reference.videos()) {
				youtubePlaylistVideoDetailsCache.add(new YoutubePlaylistVideoDetail(video));
			}
		}
		return youtubePlaylistVideoDetailsCache;
	}

	public String getPlaylistTitle() {
		return reference.details().title();
	}


}
