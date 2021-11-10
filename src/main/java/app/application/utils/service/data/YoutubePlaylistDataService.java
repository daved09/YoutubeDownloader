package app.application.utils.service.data;

import app.application.data.entities.YoutubePlaylist;
import com.github.kiulian.downloader.downloader.request.RequestPlaylistInfo;
import com.github.kiulian.downloader.model.playlist.PlaylistInfo;
import javafx.application.Platform;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class YoutubePlaylistDataService extends YoutubeDataService {

	@SneakyThrows
	public YoutubePlaylist getPlaylistInfo(String playListId) {
		RequestPlaylistInfo requestPlaylistInfo = new RequestPlaylistInfo(playListId);
		PlaylistInfo playlistInfo = youtubeDownloader.getPlaylistInfo(requestPlaylistInfo).data();
		return new YoutubePlaylist(playlistInfo);
	}


}
