package app.application.utils

import java.lang.IllegalStateException

class GlobalValues private constructor() {

    init {
        throw IllegalStateException("Value Class")
    }

    companion object {
        //Downloader Spezifisch
        const val VIDEO_DIRECTORY = "Videos"
        const val YOUTUBE_URL = "youtube.com"
        const val YOUTUBE_SHORT_URL = "youtu.be"
        const val DOWNLOADER_CONFIG_FILENAME = ".ytdl.json"

        //Exceptions
        const val DOWNLOAD_EXECUTOR_TERMINATION_ERROR = "Termination of download executor was not Successful"

        //Java Generell
        const val USER_HOME = "user.home"
    }
}