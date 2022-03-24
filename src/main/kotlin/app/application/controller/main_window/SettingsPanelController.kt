package app.application.controller.main_window

import app.application.data.VersionProperties
import app.application.utils.DialogManager
import app.application.utils.UserConfigHandler
import javafx.fxml.FXML
import javafx.scene.control.CheckBox
import javafx.scene.control.TextField
import org.springframework.stereotype.Component

@Component
class SettingsPanelController(
        private val userConfigHandler: UserConfigHandler,
        private val versionProperties: VersionProperties,
        private val dialogManager: DialogManager) {

    @FXML
    private lateinit var txtDownloadPath: TextField

    @FXML
    private lateinit var txtVerion: TextField

    @FXML
    private lateinit var chkOverwriteVideos: CheckBox

    @FXML
    private lateinit var chkSubfolderForPlaylists: CheckBox

    
    @FXML
    fun initialize() {
        txtDownloadPath.textProperty().bindBidirectional(userConfigHandler.userConfig!!.downloadDir)
        chkOverwriteVideos.selectedProperty().bindBidirectional(userConfigHandler.userConfig!!.overwriteExistingVideo)
        chkSubfolderForPlaylists.selectedProperty().bindBidirectional(userConfigHandler.userConfig!!.subFolderForPlaylists)
        txtVerion.textProperty().bind(versionProperties.version)
    }

    fun btnSaveClick() {
        userConfigHandler.writeConfig()
        dialogManager.openInformationDialog("Speichern erfolgreich", "")
    }
}