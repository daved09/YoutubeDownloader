package app.application.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.springframework.stereotype.Service;

import static javafx.scene.control.Alert.AlertType.*;

@Service
public class DialogManager {

    private ExceptionExtractor exceptionExtractor;

    public DialogManager(ExceptionExtractor exceptionExtractor) {
        this.exceptionExtractor = exceptionExtractor;
    }

    public void openWarningDialog(String title, String message){
        openDialog(WARNING, "Warnung", title, message);
    }

    public void openInformationDialog(String title, String message){
        openDialog(INFORMATION, "Information", title, message);
    }

    public void openErrorDialog(String title, String message){
        openDialog(ERROR, "Error", title, message);
    }

    public void openExceptionDialog(Exception e){
        openDialog(ERROR, "Error", "Es ist ein Fehler aufgetreten.", exceptionExtractor.getTargetOfException(e).getMessage());
    }

    private void openDialog(AlertType alertType, String title, String headerText, String contentText){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

}
