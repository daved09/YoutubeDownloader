package app.application.utils;


import com.github.kiulian.downloader.downloader.request.RequestVideoFileDownload;
import com.github.kiulian.downloader.downloader.request.RequestVideoInfo;
import com.github.kiulian.downloader.model.videos.VideoInfo;
import com.github.kiulian.downloader.model.videos.formats.Format;
import com.github.kiulian.downloader.model.videos.formats.VideoWithAudioFormat;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class YoutubeVideoDownloadService extends YoutubeDownloadService {

    @SneakyThrows
    public void downloadVideoAsync(VideoInfo videoInfo, String quality) {
        downloadAsync(videoInfo, selectAudioVideoFormat(videoInfo, quality));
    }

    public void downloadAudioOnlyAsync(VideoInfo videoInfo) {
        downloadAsync(videoInfo, videoInfo.audioFormats().get(0));
    }

    public VideoInfo getVideoInfo(String videoId){
        RequestVideoInfo requestVideoInfo = new RequestVideoInfo(videoId);
        return youtubeDownloader.getVideoInfo(requestVideoInfo).data();
    }

    @SneakyThrows
    protected void downloadAsync(VideoInfo videoInfo, Format format) {
        RequestVideoFileDownload requestVideoFileDownload = new RequestVideoFileDownload(format);
        requestVideoFileDownload.callback(youtubeDownloadListener)
                .renameTo(videoInfo.details().title())
                .overwriteIfExists(userConfigHandler.getUserConfig().getOverwriteExistingVideo().get())
                .saveTo(Paths.get(userConfigHandler.getUserConfig().getDownloadDir().get()).toFile());
        youtubeDownloader.downloadVideoFile(requestVideoFileDownload).data();
    }


    private VideoWithAudioFormat selectAudioVideoFormat(VideoInfo videoInfo, String quality) {
        List<VideoWithAudioFormat> audioVideoFormats = videoInfo.videoWithAudioFormats();
        audioVideoFormats.removeIf(audioVideoFormat -> !audioVideoFormat.qualityLabel().equals(quality));
        return audioVideoFormats.get(0);
    }

    public List<String> getQualityLabels(VideoInfo videoInfo) {
        List<VideoWithAudioFormat> audioVideoFormats = videoInfo.videoWithAudioFormats();
        List<String> qualityLabels = new ArrayList<>();
        for (VideoWithAudioFormat audioVideoFormat : audioVideoFormats) {
            qualityLabels.add(audioVideoFormat.qualityLabel());
        }
        return qualityLabels;
    }

    public void deleteUnfinishedDownload(VideoInfo videoInfo){
        if(!youtubeDownloadListener.isDownloadFinished()){
            for (File file : getFilesToDelete(Paths.get(userConfigHandler.getUserConfig().getDownloadDir().get())
                    .toFile(), videoInfo.details().title())) {
                file.delete();
            }
        }
    }

    private File[] getFilesToDelete(File dir, String fileName){
        return dir.listFiles((file, name) -> name.matches(fileName + ".*"));
    }

}
