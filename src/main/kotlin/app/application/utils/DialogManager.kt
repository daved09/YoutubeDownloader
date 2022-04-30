package app.application.utils

import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import org.springframework.stereotype.Service

@Service
class DialogManager(private val exceptionExtractor: ExceptionExtractor) {
    fun openWarningDialog(title: String, message: String?) {
        openDialog(AlertType.WARNING, "Warnung", title, message)
    }

    fun openInformationDialog(title: String, message: String?) {
        openDialog(AlertType.INFORMATION, "Information", title, message)
    }

    fun openErrorDialog(title: String, message: String?) {
        openDialog(AlertType.ERROR, "Error", title, message)
    }

    fun openExceptionDialog(e: Exception?) {
        openDialog(AlertType.ERROR, "Error", "Es ist ein Fehler aufgetreten.", exceptionExtractor.getTargetOfException(e)?.message)
    }

    private fun openDialog(alertType: AlertType, title: String, headerText: String, contentText: String?) {
        val alert = Alert(alertType)
        alert.title = title
        alert.headerText = headerText
        alert.contentText = contentText
        alert.showAndWait()
    }
}