package app.application.data.entities

abstract class YoutubeEntity<T> {
    var reference: T? = null
        protected set
}