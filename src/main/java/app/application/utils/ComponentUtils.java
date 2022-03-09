package app.application.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lombok.SneakyThrows;

public class ComponentUtils {

	private ComponentUtils(){
		throw new IllegalStateException("Utility Class");
	}

	@SneakyThrows
	public static <T extends Parent> void loadComponent(T component){
		FXMLLoader loader = new FXMLLoader();
		loader.setRoot(component);
		loader.setControllerFactory(aClass -> component);
		String fileName = "/components/" + component.getClass().getSimpleName() + ".fxml";
		loader.load(component.getClass().getResourceAsStream(fileName));
	}

}
