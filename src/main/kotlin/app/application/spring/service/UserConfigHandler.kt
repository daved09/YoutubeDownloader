package app.application.spring.service

import app.application.data.UserConfig
import app.application.utils.DOWNLOADER_CONFIG_FILENAME
import app.application.utils.USER_HOME
import com.google.gson.Gson
import lombok.SneakyThrows
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.nio.file.Files
import java.nio.file.Paths

class UserConfigHandler {
    var userConfig: UserConfig = useDefaultConfig()
        private set

    lateinit var gson: Gson

    lateinit var dialogManager: DialogManager

    private fun useDefaultConfig(): UserConfig{
        return UserConfig()
    }

    @SneakyThrows
    fun loadConfig() {
        try {
            if(Files.exists(Paths.get(USER_CONFIG_FILE))){
                userConfig = gson.fromJson(FileReader(USER_CONFIG_FILE), UserConfig::class.java)
            }
        } catch (e: Exception){
            //TODO: Handle exception here (issue #58)
        }
    }

    @SneakyThrows
    fun writeConfig() {
        val fileWriter = FileWriter(USER_CONFIG_FILE)
        gson.toJson(userConfig, fileWriter)
        fileWriter.flush()
        fileWriter.close()
    }

    companion object {
        private val USER_CONFIG_FILE = System.getProperty(USER_HOME) + File.separator + DOWNLOADER_CONFIG_FILENAME
    }
}