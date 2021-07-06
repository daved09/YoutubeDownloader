package app.application.config;


import app.application.data.ConfigrationFactory;
import app.application.utils.UserConfigHandler;
import com.github.kiulian.downloader.YoutubeDownloader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class DownloaderConfiguration {

    @Autowired
    private ConfigrationFactory configrationFactory;

    @Bean
    public YoutubeDownloader youtubeDownloader(){
        YoutubeDownloader youtubeDownloader = new YoutubeDownloader();
        youtubeDownloader.setParserRequestProperty("User-Agend",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
        youtubeDownloader.setParserRetryOnFailure(1);
        return youtubeDownloader;
    }

    @Bean
    public UserConfigHandler userConfigHandler() throws IOException {
        UserConfigHandler userConfigHandler = new UserConfigHandler();
        userConfigHandler.setConfigrationFactory(configrationFactory);
        userConfigHandler.loadConfig();
        return userConfigHandler;
    }


}
