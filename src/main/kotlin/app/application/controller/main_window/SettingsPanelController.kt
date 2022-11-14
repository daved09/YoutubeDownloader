package app.application.controller.main_window

import app.application.data.VersionProperties
import app.application.spring.service.DialogManager
import app.application.spring.service.GlobalObjectHandler
import app.application.spring.service.UpdateChecker
import app.application.spring.service.UserConfigHandler
import app.application.utils.*
import app.application.utils.DownloadExecutorHandler.DownloaderTask
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.scene.control.CheckBox
import javafx.scene.control.Hyperlink
import javafx.scene.control.TextField
import javafx.stage.DirectoryChooser
import org.springframework.stereotype.Component
import java.io.File

@Component
class SettingsPanelController(
    private val userConfigHandler: UserConfigHandler,
    private val versionProperties: VersionProperties,
    private val dialogManager: DialogManager,
    private val updateChecker: UpdateChecker,
    private val globalObjectHandler: GlobalObjectHandler
) {

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

    private val downloadExecutorHandler = DownloadExecutorHandler()

    
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
        dialogManager.openInformationDialog("Save successful", "")
    }

    fun openDownloaderPage(){
        globalObjectHandler.hostServices?.showDocument(GlobalValues.DOWNLOAD_PAGE)
    }

    fun openFileDialog(){
        val directoryChooser = DirectoryChooser()
        directoryChooser.initialDirectory = File(txtDownloadPath.text)
        val directory = directoryChooser.showDialog(txtDownloadPath.scene.window)
        if(directory != null){
            txtDownloadPath.text = directory.absolutePath
        }
    }

    private fun checkForUpdate(){
        downloadExecutorHandler.executeTask(object : DownloaderTask {
            override fun execute() {
                if(updateChecker.hasNewUpdate()){
                    Platform.runLater { lnkUpdate.isVisible = true }
                }
            }
        })
    }
}