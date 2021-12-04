package app.application.utils.service.download;

import app.application.data.entities.YoutubePlaylist;
import app.application.data.entities.YoutubeVideo;
import app.application.listener.YoutubePlaylistDownloadListener;
import app.application.utils.service.data.YoutubeVideoDataService;
import com.github.kiulian.downloader.downloader.request.RequestVideoFileDownload;
import javafx.application.Platform;
import javafx.scene.control.Label;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class YoutubePlaylistDownloadService extends YoutubeDownloadService {

    private final YoutubeVideoDataService youtubeVideoDataService;

    public YoutubePlaylistDownloadService(YoutubeVideoDataService youtubeVideoDataService) {
        this.youtubeVideoDataService = youtubeVideoDataService;
    }

    @Setter
    private Label label;

    @SneakyThrows
    public void downloadPlaylist(YoutubePlaylist youtubePlaylist){
        this.youtubeDownloadListener = new YoutubePlaylistDownloadListener(dialogManager, youtubePlaylist.getPlaylistSize());
        int size = youtubePlaylist.getPlaylistSize();
        AtomicInteger progress = new AtomicInteger(0);
        youtubePlaylist.getPlaylistVideos().stream().
                filter(video -> !video.getIgnore().get())
                .forEach(video -> {
                    progress.getAndIncrement();
                    setLabelProgress(progress.get(), size);
                    downloadAsync(youtubePlaylist.getPlaylistTitle(), youtubeVideoDataService.getYoutubeVideo(video.getVideoId()));
                });
    }

    protected void downloadAsync(String playlistTitle, YoutubeVideo youtubeVideo){
        RequestVideoFileDownload requestVideoFileDownload = new RequestVideoFileDownload(youtubeVideo.getBestVideoWithAudioFormat());
        requestVideoFileDownload.renameTo(youtubeVideo.getVideoTitle()).overwriteIfExists(true)
                .saveTo(Paths.get(userConfigHandler.getUserConfig().getDownloadDir().get() + File.separator + playlistTitle).toFile())
                .callback(youtubeDownloadListener);
        youtubeDownloader.downloadVideoFile(requestVideoFileDownload);
    }

    private void setLabelProgress(int current, int max){
        Platform.runLater(() -> label.setText("Videos: " + current + "/" + max));
    }

}
