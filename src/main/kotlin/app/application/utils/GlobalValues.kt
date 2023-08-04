package app.application.utils

class GlobalValues private constructor() {

    init {
        throw IllegalStateException("Value Class")
    }

    companion object {

        const val VIDEO_DIRECTORY = "Videos"
        const val VIDEO_DIRECTORY_MAC = "Movies"
        const val YOUTUBE_URL = "youtube.com"
        const val YOUTUBE_SHORT_URL = "youtu.be"
        const val DOWNLOADER_CONFIG_FILENAME = ".ytdl.json"
        const val DOWNLOAD_PAGE = "https://github.com/daved09/YoutubeDownloader/releases/latest"

        const val DOWNLOAD_EXECUTOR_TERMINATION_ERROR = "Termination of download executor was not Successful"

        const val USER_HOME = "user.home"
    }
}