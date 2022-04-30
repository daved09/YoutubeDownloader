package app.application.utils

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
        const val DOWNLOAD_PAGE = "http://daluba.de:3000/dave/YoutubeDownloader_releases/releases/latest"

        //Exceptions
        const val DOWNLOAD_EXECUTOR_TERMINATION_ERROR = "Termination of download executor was not Successful"

        //Java Generell
        const val USER_HOME = "user.home"
    }
}