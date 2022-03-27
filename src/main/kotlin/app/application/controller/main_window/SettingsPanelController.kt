package app.application.controller.main_window

import app.application.data.VersionProperties
import app.application.utils.*
import javafx.fxml.FXML
import javafx.scene.control.CheckBox
import javafx.scene.control.Hyperlink
import javafx.scene.control.TextField
import org.springframework.stereotype.Component

@Component
class SettingsPanelController(
        private val userConfigHandler: UserConfigHandler,
        private val versionProperties: VersionProperties,
        private val dialogManager: DialogManager,
        private val updateChecker: UpdateChecker,
        private val globalObjectHandler: GlobalObjectHandler) {

    @FXML
    private lateinit var txtDownloadPath: TextField

    @FXML
    private lateinit var txtVersion: TextField

    @FXML
    private lateinit var chkOverwriteVideos: CheckBox

    @FXML
    private lateinit var chkSubfolderForPlaylists: CheckBox

    @FXML
    private lateinit var lnkUpdate: Hyperlink

    
    @FXML
    fun initialize() {
        txtDownloadPath.textProperty().bindBidirectional(userConfigHandler.userConfig!!.downloadDir)
        chkOverwriteVideos.selectedProperty().bindBidirectional(userConfigHandler.userConfig!!.overwriteExistingVideo)
        chkSubfolderForPlaylists.selectedProperty().bindBidirectional(userConfigHandler.userConfig!!.subFolderForPlaylists)
        txtVersion.textProperty().bind(versionProperties.version)
        checkForUpdate()
    }

    fun btnSaveClick() {
        userConfigHandler.writeConfig()
        dialogManager.openInformationDialog("Speichern erfolgreich", "")
    }

    fun openDownloaderPage(){
        globalObjectHandler.hostServices?.showDocument(GlobalValues.DOWNLOAD_PAGE)
    }

    private fun checkForUpdate(){
        if(updateChecker.hasNewUpdate()){
            lnkUpdate.isVisible = true
        }
    }
}