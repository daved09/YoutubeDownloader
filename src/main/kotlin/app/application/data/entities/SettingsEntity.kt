package app.application.data.entities

import com.github.kiulian.downloader.model.videos.formats.VideoWithAudioFormat
import javafx.beans.property.BooleanProperty
import javafx.beans.property.ObjectProperty

class SettingsEntity(
    val qualityProperty: ObjectProperty<VideoWithAudioFormat>,
    val audioOnlyProperty: BooleanProperty
)