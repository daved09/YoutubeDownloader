package app.application.data.entities

class YoutubeVideoSettingsEntity(youtubeVideo: YoutubeVideo): YoutubeSettingsEntity<YoutubeVideo>() {
    init {
        this.youtubeEntity = youtubeVideo
    }
}