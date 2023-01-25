package app.application.spring.service

import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.layout.GridPane
import javafx.scene.layout.Priority
import org.springframework.stereotype.Service


@Service
class DialogManager(private val exceptionExtractor: ExceptionExtractor) {
    fun openWarningDialog(title: String, message: String?) {
        openDialog(AlertType.WARNING, "Warning", title, message)
    }

    fun openInformationDialog(title: String, message: String?) {
        openDialog(AlertType.INFORMATION, "Information", title, message)
    }

    private fun openErrorDialog(title: String, message: String?) {
        openDialog(AlertType.ERROR, "Error", title, message)
    }

    fun openExceptionDialog(e: Exception?) {
        openErrorDialog("An error has occurred.", exceptionExtractor.getTargetOfException(e)?.message)
    }

    private fun openDialog(alertType: AlertType, title: String, headerText: String, contentText: String?) {
        val alert = Alert(alertType)
        alert.title = title
        alert.headerText = headerText
        setExpandedGridTo(contentText, alert)
        alert.showAndWait()
    }

    private fun setExpandedGridTo(contentText: String?, alert: Alert) {
        val expContent = GridPane()
        expContent.maxWidth = Double.MAX_VALUE
        expContent.add(Label(contentText), 0, 0)
        alert.dialogPane.content = expContent
    }
}