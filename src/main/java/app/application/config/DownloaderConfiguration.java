package app.application.config;


import com.github.kiulian.downloader.YoutubeDownloader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DownloaderConfiguration {


    @Bean
    public YoutubeDownloader youtubeDownloader(){
        YoutubeDownloader youtubeDownloader = new YoutubeDownloader();
        youtubeDownloader.setParserRequestProperty("User-Agend",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
        youtubeDownloader.setParserRetryOnFailure(1);
        return youtubeDownloader;
    }


}
