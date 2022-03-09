package app.application.factories;

import app.application.data.UserConfig;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationFactory {

	public UserConfig createDefaultConfiguration() {
		return new UserConfig();
	}


}
