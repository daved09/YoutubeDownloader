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
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
    private Label label;

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
        setLabelProgress(0, titles.size());
        return titles;
    }

    @SneakyThrows
    public void downloadPlaylist(){
        int size = youtubePlaylist.videos().size();
        int progress = 0;
        for (PlaylistVideoDetails video : youtubePlaylist.videos()) {
            progress++;
            setLabelProgress(progress, size);
            List<Format> formats = getFormatsFromVideo(video);
            if(formats.get(1) instanceof AudioVideoFormat){
                downloadAsync(formats.get(1), video.videoId());
            }
            else{
                downloadAsync(formats.get(0), video.videoId());
            }
        }
    }

    private List<Format> getFormatsFromVideo(PlaylistVideoDetails video) {
        List<Format> formats = new ArrayList<>();
        try {
            formats = youtubeDownloader.getVideo(video.videoId()).formats();
        } catch (YoutubeException e) {
            e.printStackTrace();
        }
        return formats;
    }

    protected void downloadAsync(Format format, String videoId) throws YoutubeException, IOException {
        youtubeDownloader.getVideo(videoId).download(format,
                Paths.get(userConfigHandler.getUserConfig().getDownloadDir().get() + File.separator + youtubePlaylist.details().title()).toFile());
    }

    private void setLabelProgress(int current, int max){
        Platform.runLater(() -> label.setText("Videos: " + current + "/" + max));
    }

}
