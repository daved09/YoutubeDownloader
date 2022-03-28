package app.application.data.model

import org.springframework.stereotype.Service

@Service
class QueueManager {

    private val downloadQueue = DownloadQueue()

}