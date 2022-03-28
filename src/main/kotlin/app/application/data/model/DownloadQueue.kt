package app.application.data.model

import app.application.data.entities.YoutubeVideo
import java.util.function.Predicate

class DownloadQueue{

    private var downloadList: MutableList<YoutubeVideo> = ArrayList()

    fun addToDownloadList(youtubeVideo: YoutubeVideo){
        downloadList.add(youtubeVideo)
    }

    fun removeFromDownloadList(videoId: String){
        downloadList.removeIf{ video -> video.videoId == videoId }
    }

    fun getDownloadList(): MutableList<YoutubeVideo> {
        return downloadList
    }

    fun getDownloadListAndClear(): List<YoutubeVideo>{
        val downloadList = getDownloadList().toList()
        this.downloadList.clear()
        return downloadList
    }

}