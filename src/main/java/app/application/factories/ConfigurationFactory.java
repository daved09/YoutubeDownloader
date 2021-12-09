package app.application.factories;

import app.application.data.UserConfig;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class ConfigurationFactory {


	//TODO Pruefen ob das noch benoetigt wird
	public UserConfig createDefaultConfiguration() {
		UserConfig userConfig = new UserConfig();
		userConfig.setDownloadDir(new SimpleStringProperty(System.getProperty("user.home") + File.separator + "Videos"));
		userConfig.setOverwriteExistingVideo(new SimpleBooleanProperty(false));
		userConfig.setSubFolderForPlaylists(new SimpleBooleanProperty(true));
		return userConfig;
	}


}
