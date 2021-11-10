package app.application.utils;

import app.application.data.entities.YoutubePlaylist;
import app.application.data.entities.YoutubePlaylistVideoDetail;
import app.application.data.entities.YoutubeVideo;
import app.application.listener.YoutubePlaylistDownloadListener;
import com.github.kiulian.downloader.downloader.request.RequestPlaylistInfo;
import com.github.kiulian.downloader.downloader.request.RequestVideoFileDownload;
import com.github.kiulian.downloader.downloader.request.RequestVideoInfo;
import com.github.kiulian.downloader.model.playlist.PlaylistInfo;
import javafx.application.Platform;
import javafx.scene.control.Label;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Paths;

@Service
public class YoutubePlaylistDownloadService extends YoutubeDownloadService {

    @Setter
    private Label label;

    @SneakyThrows
    public void downloadPlaylist(YoutubePlaylist youtubePlaylist){
        this.youtubeDownloadListener = new YoutubePlaylistDownloadListener(dialogManager, youtubePlaylist.getPlaylistSize());
        int size = youtubePlaylist.getPlaylistSize();
        int progress = 0;
        for (YoutubePlaylistVideoDetail video : youtubePlaylist.getPlaylistVideos()) {
            progress++;
            setLabelProgress(progress, size);
            if(!video.getIgnore().get()){
                downloadAsync(youtubePlaylist.getPlaylistTitle(), getVideoInfo(video.getVideoId()));
            }
        }
    }


    private YoutubeVideo getVideoInfo(String videoID){
        RequestVideoInfo requestVideoInfo = new RequestVideoInfo(videoID);
        return new YoutubeVideo(youtubeDownloader.getVideoInfo(requestVideoInfo).data());
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
