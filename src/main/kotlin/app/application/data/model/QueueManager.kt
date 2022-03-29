package app.application.data.model

import app.application.data.entities.YoutubeVideo
import org.springframework.stereotype.Service

@Service
class QueueManager {

    val downloadQueue = DownloadQueue()

    var queueListener: QueueListener? = null

    fun addToDownloadList(youtubeVideo: YoutubeVideo){
        downloadQueue.addToDownloadList(youtubeVideo)
        queueListener?.queueChanged(youtubeVideo)
    }


    interface QueueListener{
        fun queueChanged(youtubeVideo: YoutubeVideo)
    }

}