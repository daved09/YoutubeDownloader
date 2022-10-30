package app.application.spring.service.download

import app.application.listener.YoutubeDownloadListener
import app.application.spring.service.DialogManager
import app.application.spring.service.UserConfigHandler
import com.github.kiulian.downloader.YoutubeDownloader
import org.springframework.beans.factory.annotation.Autowired

abstract class YoutubeDownloadService{
    @Autowired
    protected lateinit var youtubeDownloader: YoutubeDownloader

    @Autowired
    protected lateinit var userConfigHandler: UserConfigHandler

    @Autowired
    protected lateinit var dialogManager: DialogManager

    var youtubeDownloadListener: YoutubeDownloadListener? = null

}