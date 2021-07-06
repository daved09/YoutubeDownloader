package app.application.utils;


import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.YoutubeException;
import com.github.kiulian.downloader.model.VideoDetails;
import com.github.kiulian.downloader.model.YoutubeVideo;
import com.github.kiulian.downloader.model.formats.AudioVideoFormat;
import com.github.kiulian.downloader.model.formats.Format;
import com.github.kiulian.downloader.model.playlist.PlaylistDetails;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class YoutubeVideoDownloadService extends YoutubeDownloadService {

    private YoutubeVideo youtubeVideo;

    private YoutubeDownloadListener youtubeDownloadListener;


    @SneakyThrows
    public void downloadVideoAsync(String quality) {
        downloadAsync(selectAudioVideoFormat(quality));
    }

    public void downloadAudioOnlyAsync() {
        downloadAsync(youtubeVideo.audioFormats().get(0));
    }


    @SneakyThrows
    public VideoDetails getVideoDetails(String id) {
        this.youtubeVideo = youtubeDownloader.getVideo(id);
        return youtubeVideo.details();
    }

    @SneakyThrows
    private void downloadAsync(Format format) {
        youtubeVideo.downloadAsync(format, Paths.get(userConfigHandler.getUserConfig().getDownloadDir().get()).toFile(), youtubeDownloadListener);
    }


    private AudioVideoFormat selectAudioVideoFormat(String quality) {
        List<AudioVideoFormat> audioVideoFormats = youtubeVideo.videoWithAudioFormats();
        audioVideoFormats.removeIf(audioVideoFormat -> !audioVideoFormat.qualityLabel().equals(quality));
        return audioVideoFormats.get(0);
    }

    public YoutubeVideo getVideo() {
        return youtubeVideo;
    }

    public List<String> getQualityLabels() {
        List<AudioVideoFormat> audioVideoFormats = youtubeVideo.videoWithAudioFormats();
        List<String> qualityLabels = new ArrayList<>();
        for (AudioVideoFormat audioVideoFormat : audioVideoFormats) {
            qualityLabels.add(audioVideoFormat.qualityLabel());
        }
        return qualityLabels;
    }

    public void setYoutubeDownloadListener(YoutubeDownloadListener youtubeDownloadListener) {
        this.youtubeDownloadListener = youtubeDownloadListener;
    }
}
