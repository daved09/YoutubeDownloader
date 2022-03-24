package app.application

import org.springframework.context.ConfigurableApplicationContext
import kotlin.Throws
import org.springframework.boot.builder.SpringApplicationBuilder
import app.MyApp
import net.rgielen.fxweaver.core.FxWeaver
import app.application.controller.main_window.MainWindowController
import app.application.utils.GlobalObjectHandler
import javafx.application.Application
import javafx.application.Platform
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import java.lang.Exception
import kotlin.system.exitProcess

class YoutubeDownloader : Application() {
    private var applicationContext: ConfigurableApplicationContext? = null
    @Throws(Exception::class)
    override fun init() {
        applicationContext = SpringApplicationBuilder().sources(MyApp::class.java)
                .run(*parameters.raw.toTypedArray())
    }

    @Throws(Exception::class)
    override fun start(stage: Stage) {
        setupHostServices()
        val fxWeaver = applicationContext!!.getBean(FxWeaver::class.java)
        val root = fxWeaver.loadView<MainWindowController, Parent>(MainWindowController::class.java)
        val scene = Scene(root)
        stage.title = "Youtube Downloader"
        stage.scene = scene
        stage.show()
    }

    @Throws(Exception::class)
    override fun stop() {
        applicationContext!!.close()
        Platform.exit()
        exitProcess(0)
    }

    private fun setupHostServices() {
        val globalObjectHandler = applicationContext!!.getBean(GlobalObjectHandler::class.java)
        globalObjectHandler.hostServices = hostServices
    }
}