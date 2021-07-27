package app.application.components;

import app.application.utils.FxmlUtils;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

public class VideoElement extends AnchorPane {

	@FXML
	private ImageView imgThumbnail;

	public VideoElement() {
		FxmlUtils.load(this);
	}

	public void setThumpNail(Image image){
		imgThumbnail.setImage(image);
	}

}
