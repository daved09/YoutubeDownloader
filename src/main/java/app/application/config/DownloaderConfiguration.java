package app.application.config;

import app.application.factories.ConfigurationFactory;
import app.application.utils.UserConfigHandler;
import com.github.kiulian.downloader.Config;
import com.github.kiulian.downloader.YoutubeDownloader;
import com.google.gson.Gson;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

@Configuration
@EnableConfigurationProperties
public class DownloaderConfiguration {

    private final ConfigurationFactory configurationFactory;

    private final Gson gson;

    public DownloaderConfiguration(ConfigurationFactory configurationFactory, Gson gson) {
        this.configurationFactory = configurationFactory;
        this.gson = gson;
    }

    @Bean
    public YoutubeDownloader youtubeDownloader(){
        Config config = new Config.Builder().maxRetries(1).executorService(Executors.newCachedThreadPool()).build();
        return new YoutubeDownloader(config);
    }

    @Bean
    public UserConfigHandler userConfigHandler() {
        UserConfigHandler userConfigHandler = new UserConfigHandler();
        userConfigHandler.setConfigurationFactory(configurationFactory);
        userConfigHandler.setGson(gson);
        userConfigHandler.loadConfig();
        return userConfigHandler;
    }



}
