package app.application.utils;


import com.github.kiulian.downloader.downloader.request.RequestVideoFileDownload;
import com.github.kiulian.downloader.downloader.request.RequestVideoInfo;
import com.github.kiulian.downloader.model.videos.VideoDetails;
import com.github.kiulian.downloader.model.videos.VideoInfo;
import com.github.kiulian.downloader.model.videos.formats.Format;
import com.github.kiulian.downloader.model.videos.formats.VideoWithAudioFormat;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class YoutubeVideoDownloadService extends YoutubeDownloadService {

    private VideoInfo videoInfo;

    private YoutubeDownloadListener youtubeDownloadListener;


    @SneakyThrows
    public void downloadVideoAsync(String quality) {
        downloadAsync(selectAudioVideoFormat(quality));
    }

    public void downloadAudioOnlyAsync() {
        downloadAsync(videoInfo.audioFormats().get(0));
    }


    @SneakyThrows
    public VideoDetails getVideoDetails(String id) {
        RequestVideoInfo requestVideoInfo = new RequestVideoInfo(id);
        this.videoInfo = youtubeDownloader.getVideoInfo(requestVideoInfo).data();
        return videoInfo.details();
    }

    @SneakyThrows
    protected void downloadAsync(Format format) {
        RequestVideoFileDownload requestVideoFileDownload = new RequestVideoFileDownload(format);
        requestVideoFileDownload.callback(youtubeDownloadListener)
                .renameTo(videoInfo.details().title()).async()
                .saveTo(Paths.get(userConfigHandler.getUserConfig().getDownloadDir().get()).toFile());
        youtubeDownloader.downloadVideoFile(requestVideoFileDownload).data();
    }


    private VideoWithAudioFormat selectAudioVideoFormat(String quality) {
        List<VideoWithAudioFormat> audioVideoFormats = videoInfo.videoWithAudioFormats();
        audioVideoFormats.removeIf(audioVideoFormat -> !audioVideoFormat.qualityLabel().equals(quality));
        return audioVideoFormats.get(0);
    }

    public VideoInfo getVideo() {
        return videoInfo;
    }

    public List<String> getQualityLabels() {
        List<VideoWithAudioFormat> audioVideoFormats = videoInfo.videoWithAudioFormats();
        List<String> qualityLabels = new ArrayList<>();
        for (VideoWithAudioFormat audioVideoFormat : audioVideoFormats) {
            qualityLabels.add(audioVideoFormat.qualityLabel());
        }
        return qualityLabels;
    }

    public void setYoutubeDownloadListener(YoutubeDownloadListener youtubeDownloadListener) {
        this.youtubeDownloadListener = youtubeDownloadListener;
    }
}
