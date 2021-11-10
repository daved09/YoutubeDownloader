package app.application.utils.service;


import app.application.listener.YoutubeDownloadListener;
import app.application.utils.DialogManager;
import app.application.utils.UserConfigHandler;
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
