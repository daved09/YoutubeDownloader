package app.application.utils

import javafx.scene.control.Alert
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
class DialogManager(private val exceptionExtractor: ExceptionExtractor) {
    fun openWarningDialog(title: String, message: String?) {
        openDialog(Alert.AlertType.WARNING, "Warnung", title, message)
    }

    fun openInformationDialog(title: String, message: String?) {
        openDialog(Alert.AlertType.INFORMATION, "Information", title, message)
    }

    fun openErrorDialog(title: String, message: String?) {
        openDialog(Alert.AlertType.ERROR, "Error", title, message)
    }

    fun openExceptionDialog(e: Exception?) {
        openDialog(Alert.AlertType.ERROR, "Error", "Es ist ein Fehler aufgetreten.", exceptionExtractor.getTargetOfException(e)!!.message)
    }

    private fun openDialog(alertType: Alert.AlertType, title: String, headerText: String, contentText: String?) {
        val alert = Alert(alertType)
        alert.title = title
        alert.headerText = headerText
        alert.contentText = contentText
        alert.showAndWait()
    }
}