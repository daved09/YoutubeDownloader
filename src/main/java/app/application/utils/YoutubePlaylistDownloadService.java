package app.application.utils;

import com.github.kiulian.downloader.YoutubeException;
import com.github.kiulian.downloader.model.formats.Format;
import com.github.kiulian.downloader.model.formats.VideoFormat;
import com.github.kiulian.downloader.model.playlist.PlaylistDetails;
import com.github.kiulian.downloader.model.playlist.PlaylistVideoDetails;
import com.github.kiulian.downloader.model.playlist.YoutubePlaylist;
import com.github.kiulian.downloader.model.quality.VideoQuality;
import com.github.kiulian.downloader.parser.Parser;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<String> getVideoTitles(){
        List<String> titles = new ArrayList<>();
        for (PlaylistVideoDetails video : youtubePlaylist.videos()) {
            titles.add(video.title());
        }
        return titles;
    }

    @SneakyThrows
    public void downloadPlaylist(){
        for (PlaylistVideoDetails video : youtubePlaylist.videos()) {
            List<Format> formats = youtubeDownloader.getVideo(video.videoId()).formats();
            System.out.println(formats);
        }
    }

    @Override
    protected void downloadAsync(Format format) {
    }
}
