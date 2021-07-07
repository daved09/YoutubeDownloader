package app.application.utils;

import com.github.kiulian.downloader.YoutubeException;
import com.github.kiulian.downloader.model.formats.AudioVideoFormat;
import com.github.kiulian.downloader.model.formats.Format;
import com.github.kiulian.downloader.model.formats.VideoFormat;
import com.github.kiulian.downloader.model.playlist.PlaylistDetails;
import com.github.kiulian.downloader.model.playlist.PlaylistVideoDetails;
import com.github.kiulian.downloader.model.playlist.YoutubePlaylist;
import com.github.kiulian.downloader.model.quality.VideoQuality;
import com.github.kiulian.downloader.parser.Parser;
import javafx.scene.control.ProgressBar;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class YoutubePlaylistDownloadService extends YoutubeDownloadService {

    private YoutubePlaylist youtubePlaylist;

    @Setter
    private ProgressBar progressBar;

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
            List<Format> formats = null;
            try {
                formats = youtubeDownloader.getVideo(video.videoId()).formats();
            } catch (YoutubeException e) {
                e.printStackTrace();
            }
            if(formats.get(1) instanceof AudioVideoFormat){
                downloadAsync(formats.get(1), video.videoId());
            }
            else{
                downloadAsync(formats.get(0), video.videoId());
            }
        }
    }

    protected void downloadAsync(Format format, String videoId) throws YoutubeException, IOException {
        youtubeDownloader.getVideo(videoId).downloadAsync(format,
                Paths.get(userConfigHandler.getUserConfig().getDownloadDir().get() + File.separator + youtubePlaylist.details().title()).toFile());
    }
}
