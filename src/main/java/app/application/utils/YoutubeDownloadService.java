package app.application.utils;


import com.github.kiulian.downloader.YoutubeDownloader;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public abstract class YoutubeDownloadService {

    @Autowired
    protected YoutubeDownloader youtubeDownloader;

    @Autowired
    protected UserConfigHandler userConfigHandler;


}
