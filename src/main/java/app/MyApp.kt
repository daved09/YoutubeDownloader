package app

import app.application.YoutubeDownloader
import javafx.application.Application
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class MyApp
    fun main(args: Array<String>) {
        Application.launch(YoutubeDownloader::class.java, *args)
    }