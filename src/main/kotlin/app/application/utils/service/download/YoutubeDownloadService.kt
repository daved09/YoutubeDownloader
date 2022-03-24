package app.application.utils.service.download

import app.application.listener.YoutubeDownloadListener
import app.application.utils.DialogManager
import app.application.utils.UserConfigHandler
import com.github.kiulian.downloader.YoutubeDownloader
import lombok.Setter
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