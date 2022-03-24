package app.application.utils

import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.util.Callback
import java.lang.IllegalStateException
import lombok.SneakyThrows

class ComponentUtils private constructor() {
    init {
        throw IllegalStateException("Utility Class")
    }

    companion object {
        @JvmStatic
				@SneakyThrows
        fun <T : Parent?> loadComponent(component: T?) {
            val loader = FXMLLoader()
            loader.setRoot(component)
            loader.controllerFactory = Callback { aClass: Class<*>? -> component }
            val fileName = "/components/" + component!!::class.simpleName + ".fxml"
            loader.load<Any>(component.javaClass.getResourceAsStream(fileName))
        }
    }
}