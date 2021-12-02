package app.application.controller;

import app.application.data.entities.YoutubeVideo;
import app.application.utils.service.data.YoutubeVideoDataService;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

	private final FxWeaver fxWeaver;

	private final YoutubeVideoDataService youtubeVideoDataService;

	public VideoDetailsController(
					FxWeaver fxWeaver, YoutubeVideoDataService youtubeVideoDataService) {
		this.fxWeaver = fxWeaver;
		this.youtubeVideoDataService = youtubeVideoDataService;
	}

	public void setVideoInfos(String videoId) {
		YoutubeVideo youtubeVideo = youtubeVideoDataService.getYoutubeVideo(videoId);
		imgThumbnail.setImage(new Image(youtubeVideo.getVideoThumbnailUrl()));
		txtVideoDescription.setText(youtubeVideo.getVideoDescription());
		lblVideoTitle.setText(youtubeVideo.getVideoTitle());
	}

	public void open(){
		Stage stage = new Stage();
		Parent root = fxWeaver.loadView(VideoDetailsController.class);
		Scene scene = new Scene(root);
		stage.setTitle("Videodetails");
		stage.setScene(scene);
		stage.show();
	}

}
