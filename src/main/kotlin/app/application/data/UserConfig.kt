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

    var downloadDir: StringProperty = SimpleStringProperty(System.getProperty("user.home") + File.separator + GlobalValues.VIDEO_DIRECTORY)
    val overwriteExistingVideo: BooleanProperty
    val subFolderForPlaylists: BooleanProperty

    init {
        overwriteExistingVideo = SimpleBooleanProperty(false)
        subFolderForPlaylists = SimpleBooleanProperty(true)
    }
}