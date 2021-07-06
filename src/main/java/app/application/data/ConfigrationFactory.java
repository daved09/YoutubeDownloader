package app.application.data;

import javafx.beans.property.SimpleStringProperty;
import org.springframework.stereotype.Service;

@Service
public class ConfigrationFactory {



	public UserConfig createDefaultConfiguration() {
		UserConfig userConfig = new UserConfig();
		userConfig.setDownloadDir(new SimpleStringProperty(System.getProperty("user.home") + "/Videos"));
		return userConfig;
	}


}
