package app.application.spring.service

import app.application.data.UserConfig
import app.application.utils.GlobalValues
import com.google.gson.Gson
import lombok.SneakyThrows
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class UserConfigHandler {
    var userConfig: UserConfig? = null
        private set

    var gson: Gson? = null

    private fun initConfig() {
        userConfig = loadDefaultConfiguration()
//        userConfig?.downloadDir?.set(System.getProperty(GlobalValues.USER_HOME) + File.separator + GlobalValues.VIDEO_DIRECTORY)
    }

    private fun loadDefaultConfiguration(): UserConfig{
        return gson!!.fromJson(FileReader(DEFAULT_CONFIG_FILE), UserConfig::class.java)
    }

    @SneakyThrows
    fun loadConfig() {
        try {
            userConfig = gson!!.fromJson(FileReader(USER_CONFIG_FILE), UserConfig::class.java)
        } catch (e: Exception) {
            initConfig()
        }
    }

    @SneakyThrows
    fun writeConfig() {
        val fileWriter = FileWriter(USER_CONFIG_FILE)
        gson!!.toJson(userConfig, fileWriter)
        fileWriter.flush()
        fileWriter.close()
    }

    companion object {
        private val USER_CONFIG_FILE = System.getProperty(GlobalValues.USER_HOME) + File.separator + GlobalValues.DOWNLOADER_CONFIG_FILENAME
        private val DEFAULT_CONFIG_FILE = UserConfigHandler::class.java.classLoader.getResource("default/.ytdl.json")!!.file
    }
}