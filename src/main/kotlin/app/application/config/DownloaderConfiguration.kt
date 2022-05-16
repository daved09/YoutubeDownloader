package app.application.config

import app.application.spring.factories.ConfigurationFactory
import app.application.spring.service.UserConfigHandler
import com.github.kiulian.downloader.Config
import com.github.kiulian.downloader.YoutubeDownloader
import com.google.gson.Gson
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.Executors

@Configuration
@EnableConfigurationProperties
open class DownloaderConfiguration(private val configurationFactory: ConfigurationFactory, private val gson: Gson) {

    @Bean
    open fun youtubeDownloader(): YoutubeDownloader {
        val config = Config.Builder().maxRetries(1).executorService(Executors.newCachedThreadPool()).build()
        return YoutubeDownloader(config)
    }

    @Bean
    open fun userConfigHandler(): UserConfigHandler {
        val userConfigHandler = UserConfigHandler()
        userConfigHandler.configurationFactory = configurationFactory
        userConfigHandler.gson = gson
        userConfigHandler.loadConfig()
        return userConfigHandler
    }
}