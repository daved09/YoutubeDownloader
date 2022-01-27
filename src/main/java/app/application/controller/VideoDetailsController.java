package app.application.controller;

import app.application.data.entities.YoutubeVideo;
import app.application.utils.GlobalObjectHandler;
import app.application.utils.service.data.YoutubeVideoDataService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/views/VideoDetails.fxml")
public class VideoDetailsController {

	@FXML
	private ImageView imgThumbnail;

	@FXML
	private TextArea txtVideoDescription;

	@FXML
	private Label lblVideoTitle;

	@FXML
	private Hyperlink videoLink;

	private final FxWeaver fxWeaver;

	private final YoutubeVideoDataService youtubeVideoDataService;

	private final GlobalObjectHandler globalObjectHandler;

	public VideoDetailsController(
					FxWeaver fxWeaver, YoutubeVideoDataService youtubeVideoDataService, GlobalObjectHandler globalObjectHandler) {
		this.fxWeaver = fxWeaver;
		this.youtubeVideoDataService = youtubeVideoDataService;
		this.globalObjectHandler = globalObjectHandler;
	}

	public void setVideoInfos(String videoId) {
		YoutubeVideo youtubeVideo = youtubeVideoDataService.getYoutubeVideo(videoId);
		imgThumbnail.setImage(new Image(youtubeVideo.getVideoThumbnailUrl()));
		txtVideoDescription.setText(youtubeVideo.getVideoDescription());
		lblVideoTitle.setText(youtubeVideo.getVideoTitle());
		videoLink.setText("https://youtube.com/watch?v=" + videoId);
	}

	public void open(){
		Stage stage = new Stage();
		Parent root = fxWeaver.loadView(VideoDetailsController.class);
		Scene scene = new Scene(root);
		stage.setTitle("Videodetails");
		stage.setScene(scene);
		stage.show();
	}

	public void showInBrowser(ActionEvent event) {
		globalObjectHandler.getHostServices().showDocument(videoLink.getText());
	}

}
