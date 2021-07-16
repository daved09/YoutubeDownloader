package app.application.utils;


import com.github.kiulian.downloader.YoutubeDownloader;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class YoutubeDownloadService {

    @Autowired
    protected YoutubeDownloader youtubeDownloader;

    @Autowired
    protected UserConfigHandler userConfigHandler;


}
