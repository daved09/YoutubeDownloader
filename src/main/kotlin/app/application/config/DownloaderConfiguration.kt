package app.application.config

import app.application.exception.controller.ExceptionController
import app.application.spring.service.DialogManager
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
open class DownloaderConfiguration(private val gson: Gson,
private val dialogManager: DialogManager,
private val exceptionController: ExceptionController) {

    @Bean
    open fun youtubeDownloader(): YoutubeDownloader {
        exceptionController.error() //important for error handling
        val config = Config.Builder().maxRetries(1).executorService(Executors.newCachedThreadPool()).build()
        return YoutubeDownloader(config)
    }

    @Bean
    open fun userConfigHandler(): UserConfigHandler {
        val userConfigHandler = UserConfigHandler()
        userConfigHandler.gson = gson
        userConfigHandler.dialogManager = dialogManager
        userConfigHandler.loadConfig()
        return userConfigHandler
    }
}