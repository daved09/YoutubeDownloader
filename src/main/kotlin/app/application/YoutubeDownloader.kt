package app.application

import app.MyApp
import app.application.controller.main_window.MainWindowController
import app.application.spring.service.GlobalObjectHandler
import javafx.application.Application
import javafx.application.Platform
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import net.rgielen.fxweaver.core.FxWeaver
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.ConfigurableApplicationContext
import kotlin.system.exitProcess

class YoutubeDownloader : Application() {
    private lateinit var applicationContext: ConfigurableApplicationContext
    @Throws(Exception::class)
    override fun init() {
        applicationContext = SpringApplicationBuilder().sources(MyApp::class.java)
                .run(*parameters.raw.toTypedArray())
    }

    @Throws(Exception::class)
    override fun start(stage: Stage) {
        setupHostServices()
        val fxWeaver = applicationContext.getBean(FxWeaver::class.java)
        val root = fxWeaver.loadView<MainWindowController, Parent>(MainWindowController::class.java)
        val scene = Scene(root)
        stage.title = "Youtube Downloader"
        stage.scene = scene
        stage.show()
    }

    @Throws(Exception::class)
    override fun stop() {
        applicationContext.close()
        Platform.exit()
        exitProcess(0)
    }

    private fun setupHostServices() {
        val globalObjectHandler = applicationContext.getBean(GlobalObjectHandler::class.java)
        globalObjectHandler.hostServices = hostServices
    }
}