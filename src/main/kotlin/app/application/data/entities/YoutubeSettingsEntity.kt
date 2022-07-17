package app.application.data.entities

abstract class YoutubeSettingsEntity<T> {

    var settingsEntity: SettingsEntity? = null
    var youtubeEntity: T? = null
}