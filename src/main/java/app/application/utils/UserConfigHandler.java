package app.application.utils;

import app.application.factories.ConfigurationFactory;
import app.application.data.UserConfig;
import com.google.gson.Gson;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UserConfigHandler {

	private UserConfig userConfig;

	@Setter
	private ConfigurationFactory configurationFactory;

	@Setter
	private Gson gson;

	private final String CONFIG_FILE = System.getProperty("user.home") + File.separator + ".ytdl.json";

	public void initConfig(){
		userConfig = configurationFactory.createDefaultConfiguration();
	}

	@SneakyThrows
	public void loadConfig() {
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
