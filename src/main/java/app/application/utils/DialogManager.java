package app.application.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.springframework.stereotype.Service;

import static javafx.scene.control.Alert.AlertType.INFORMATION;
import static javafx.scene.control.Alert.AlertType.WARNING;

@Service
public class DialogManager {

    public void openWarningDialog(String title, String message){
        openDialog(WARNING, "Warnung", title, message);
    }

    public void openInformationDialog(String title, String message){
        openDialog(INFORMATION, "Information", title, message);
    }

    public void openDialog(AlertType alertType, String title, String headerText, String contentText){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

}
