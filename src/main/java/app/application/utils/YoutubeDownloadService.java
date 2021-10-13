package app.application.utils;


import app.application.listener.YoutubeDownloadListener;
import com.github.kiulian.downloader.YoutubeDownloader;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class YoutubeDownloadService {

    @Autowired
    protected YoutubeDownloader youtubeDownloader;

    @Autowired
    protected UserConfigHandler userConfigHandler;

    @Autowired
    protected DialogManager dialogManager;

    @Setter
    protected YoutubeDownloadListener youtubeDownloadListener;

}
