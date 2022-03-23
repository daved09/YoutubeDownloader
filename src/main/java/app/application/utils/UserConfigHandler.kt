package app.application.utils

import app.application.data.UserConfig
import app.application.factories.ConfigurationFactory
import com.google.gson.Gson
import lombok.SneakyThrows
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class UserConfigHandler {
    var userConfig: UserConfig? = null
        private set

    var configurationFactory: ConfigurationFactory? = null

    var gson: Gson? = null

    private fun initConfig() {
        userConfig = configurationFactory!!.createDefaultConfiguration()
        writeConfig()
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
    }
}