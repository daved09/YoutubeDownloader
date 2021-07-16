package app.application.data;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Data;

@Data
public class UserConfig {

	private StringProperty downloadDir;

	private BooleanProperty overwriteExistingVideo;

}
