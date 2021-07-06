package app.application.event;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TextValueChangedEventHandler {

	private TextField textField;
	private String valueToUpdate;

	public void handleValueChanged() {
		valueToUpdate = textField.getText();
	}

}
