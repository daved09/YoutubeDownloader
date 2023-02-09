package app.application.data

import app.application.utils.GlobalValues
import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import lombok.Data
import org.apache.commons.lang3.SystemUtils
import java.io.File

@Data
class UserConfig {

    var downloadDir: StringProperty
    var overwriteExistingVideo: BooleanProperty
    var subFolderForPlaylists: BooleanProperty

    init {
        downloadDir = initDefaultVideosDir()
        overwriteExistingVideo = SimpleBooleanProperty(false)
        subFolderForPlaylists = SimpleBooleanProperty(true)
    }

    private fun initDefaultVideosDir(): StringProperty {
        val dirBuilder = StringBuilder(System.getProperty("user.home") + File.separator)
        if(SystemUtils.IS_OS_MAC){
            dirBuilder.append(GlobalValues.VIDEO_DIRECTORY_MAC)
        }else{
            dirBuilder.append(GlobalValues.VIDEO_DIRECTORY)
        }
        return SimpleStringProperty(dirBuilder.toString())
    }

}