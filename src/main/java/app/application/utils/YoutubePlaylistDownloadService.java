package app.application.utils;

import app.application.data.Video;
import app.application.listener.YoutubePlaylistDownloadListener;
import com.github.kiulian.downloader.downloader.request.RequestPlaylistInfo;
import com.github.kiulian.downloader.downloader.request.RequestVideoFileDownload;
import com.github.kiulian.downloader.downloader.request.RequestVideoInfo;
import com.github.kiulian.downloader.model.playlist.PlaylistInfo;
import com.github.kiulian.downloader.model.videos.VideoInfo;
import javafx.application.Platform;
import javafx.scene.control.Label;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

@Service
public class YoutubePlaylistDownloadService extends YoutubeDownloadService {

    @Autowired
    private DialogManager dialogManager;

    @Setter
    private Label label;

    @SneakyThrows
    public PlaylistInfo getPlaylistInfo(String playListId) {
        RequestPlaylistInfo requestPlaylistInfo = new RequestPlaylistInfo(playListId);
        PlaylistInfo playlistInfo = youtubeDownloader.getPlaylistInfo(requestPlaylistInfo).data();
        setLabelProgress(0, playlistInfo.videos().size());
        this.youtubeDownloadListener = new YoutubePlaylistDownloadListener(dialogManager, playlistInfo.videos().size());
        return playlistInfo;
    }

    @SneakyThrows
    public void downloadPlaylist(String title, List<Video> videoList){
        int size = videoList.size();
        int progress = 0;
        for (Video video : videoList) {
            progress++;
            setLabelProgress(progress, size);
            if(!video.getIgnore().get()){
                downloadAsync(title, getVideoInfo(video.getPlaylistVideoDetails().videoId()));
            }
        }
    }


    private VideoInfo getVideoInfo(String videoID){
        RequestVideoInfo requestVideoInfo = new RequestVideoInfo(videoID);
        return youtubeDownloader.getVideoInfo(requestVideoInfo).data();
    }

    protected void downloadAsync(String playlistTitle, VideoInfo videoInfo){
        RequestVideoFileDownload requestVideoFileDownload = new RequestVideoFileDownload(videoInfo.bestVideoWithAudioFormat());
        requestVideoFileDownload.renameTo(videoInfo.details().title()).overwriteIfExists(true)
                .saveTo(Paths.get(userConfigHandler.getUserConfig().getDownloadDir().get() + File.separator + playlistTitle).toFile())
                .callback(youtubeDownloadListener);
        youtubeDownloader.downloadVideoFile(requestVideoFileDownload);
    }

    private void setLabelProgress(int current, int max){
        Platform.runLater(() -> label.setText("Videos: " + current + "/" + max));
    }

}
