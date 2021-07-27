package app.application.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class FxmlUtils {

	@SneakyThrows
	public static <T extends Parent> void load(T component){
		FXMLLoader loader = new FXMLLoader();
		loader.setRoot(component);
		loader.setControllerFactory(aClass -> component);
		String fileName = "/views/" + component.getClass().getSimpleName() + ".fxml";

		loader.load(component.getClass().getResourceAsStream(fileName));
	}

}
