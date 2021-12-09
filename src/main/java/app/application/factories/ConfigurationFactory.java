package app.application.factories;

import app.application.data.UserConfig;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class ConfigurationFactory {

	public UserConfig createDefaultConfiguration() {
		return new UserConfig();
	}


}
