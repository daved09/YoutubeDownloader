package app.application.data

import app.application.utils.GlobalValues
import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import lombok.Data
import java.io.File

@Data
class UserConfig {

    val downloadDir: StringProperty
    val overwriteExistingVideo: BooleanProperty
    val subFolderForPlaylists: BooleanProperty

    init {
        downloadDir = SimpleStringProperty(System.getProperty("user.home") + File.separator + GlobalValues.VIDEO_DIRECTORY)
        overwriteExistingVideo = SimpleBooleanProperty(false)
        subFolderForPlaylists = SimpleBooleanProperty(true)
    }
}