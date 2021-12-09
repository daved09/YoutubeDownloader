package app.application.data;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Data;

import java.io.File;

@Data
public class UserConfig {

	private StringProperty downloadDir;

	private BooleanProperty overwriteExistingVideo;

	private BooleanProperty subFolderForPlaylists;

	public UserConfig() {
		downloadDir = new SimpleStringProperty(System.getProperty("user.home") + File.separator + "Videos");
		overwriteExistingVideo = new SimpleBooleanProperty(false);
		subFolderForPlaylists = new SimpleBooleanProperty(true);
	}
}
