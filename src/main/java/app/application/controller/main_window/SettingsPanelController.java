package app.application.controller.main_window;

import app.application.data.VersionProperties;
import app.application.utils.DialogManager;
import app.application.utils.UserConfigHandler;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SettingsPanelController {

	@FXML
	private TextField txtDownloadPath;

	@FXML
	private TextField txtVerion;

	@FXML
	private CheckBox chkOverwriteVideos;

	@Autowired
	private UserConfigHandler userConfigHandler;

	@Autowired
	private VersionProperties versionProperties;

	@Autowired
	private DialogManager dialogManager;

	@FXML
	public void initialize(){
		txtDownloadPath.textProperty().bindBidirectional(userConfigHandler.getUserConfig().getDownloadDir());
		chkOverwriteVideos.selectedProperty().bindBidirectional(userConfigHandler.getUserConfig().getOverwriteExistingVideo());
		txtVerion.textProperty().bind(versionProperties.getVersion());
	}

	public void btnSave_click(){
		userConfigHandler.writeConfig();
		dialogManager.openInformationDialog("Speichern erfolgreich", "");
	}

}
