package app.application.utils;

import javafx.scene.control.Alert;
import org.springframework.stereotype.Service;

@Service
public class DialogManager {

    public void openWarningDialog(String title, String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warnung");
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
