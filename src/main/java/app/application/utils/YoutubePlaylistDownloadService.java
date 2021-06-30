package app.application.utils;

import com.github.kiulian.downloader.YoutubeException;
import com.github.kiulian.downloader.model.playlist.PlaylistDetails;
import com.github.kiulian.downloader.model.playlist.YoutubePlaylist;
import com.github.kiulian.downloader.parser.Parser;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class YoutubePlaylistDownloadService extends YoutubeDownloadService {

    private YoutubePlaylist youtubePlaylist;


    @SneakyThrows
    public PlaylistDetails getPlaylistDetails(String playListId) {
        this.youtubePlaylist = youtubeDownloader.getPlaylist(playListId);
        return youtubePlaylist.details();
    }


    public YoutubePlaylist getYoutubePlaylist() {
        return youtubePlaylist;
    }
}
