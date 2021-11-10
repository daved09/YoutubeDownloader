package app.application.utils.service;

import app.application.data.entities.YoutubeVideo;
import com.github.kiulian.downloader.downloader.request.RequestVideoFileDownload;
import com.github.kiulian.downloader.model.videos.formats.Format;
import com.github.kiulian.downloader.model.videos.formats.VideoWithAudioFormat;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

@Service
public class YoutubeVideoDownloadService extends YoutubeDownloadService {

    @SneakyThrows
    public void downloadVideoAsync(YoutubeVideo youtubeVideo, String quality) {
        downloadAsync(youtubeVideo, selectAudioVideoFormat(youtubeVideo, quality));
    }

    public void downloadAudioOnlyAsync(YoutubeVideo youtubeVideo) {
        downloadAsync(youtubeVideo, youtubeVideo.getAudioFormat());
    }



    @SneakyThrows
    protected void downloadAsync(YoutubeVideo youtubeVideo, Format format) {
        RequestVideoFileDownload requestVideoFileDownload = new RequestVideoFileDownload(format);
        requestVideoFileDownload.callback(youtubeDownloadListener)
                .renameTo(youtubeVideo.getVideoTitle())
                .overwriteIfExists(userConfigHandler.getUserConfig().getOverwriteExistingVideo().get())
                .saveTo(Paths.get(userConfigHandler.getUserConfig().getDownloadDir().get()).toFile());
        youtubeDownloader.downloadVideoFile(requestVideoFileDownload).data();
    }


    private VideoWithAudioFormat selectAudioVideoFormat(YoutubeVideo youtubeVideo, String quality) {
        List<VideoWithAudioFormat> audioVideoFormats = youtubeVideo.getVideoWithAudioFormat();
        audioVideoFormats.removeIf(audioVideoFormat -> !audioVideoFormat.qualityLabel().equals(quality));
        return audioVideoFormats.get(0);
    }

    public void deleteUnfinishedDownload(YoutubeVideo youtubeVideo){
        if(!youtubeDownloadListener.isDownloadFinished()){
            for (File file : getFilesToDelete(Paths.get(userConfigHandler.getUserConfig().getDownloadDir().get())
                    .toFile(), youtubeVideo.getVideoTitle())) {
                file.delete();
            }
        }
    }

    private File[] getFilesToDelete(File dir, String fileName){
        return dir.listFiles((file, name) -> name.matches(fileName + ".*"));
    }

}
