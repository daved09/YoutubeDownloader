package app.application.utils;

import app.application.data.ConfigurationFactory;
import app.application.data.UserConfig;
import com.google.gson.Gson;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UserConfigHandler {

	private UserConfig userConfig;

	@Setter
	private ConfigurationFactory configurationFactory;

	@Setter
	private Gson gson;

	private final String CONFIG_FILE = System.getProperty("user.home") + "/.ytdl.json";

	public void initConfig(){
		userConfig = configurationFactory.createDefaultConfiguration();
	}

	public void loadConfig() throws IOException {
		if(!Files.exists(Paths.get(CONFIG_FILE))){
			initConfig();
			writeConfig();
		}
		userConfig = gson.fromJson(new FileReader(CONFIG_FILE), UserConfig.class);
	}

	@SneakyThrows
	public void writeConfig() {
		FileWriter fileWriter = new FileWriter(CONFIG_FILE);
		gson.toJson(userConfig, fileWriter);
		fileWriter.flush();
		fileWriter.close();
	}

	public UserConfig getUserConfig() {
		return userConfig;
	}

}
