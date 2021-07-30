package app.application.controller;

import app.application.utils.YoutubeVideoDownloadService;
import com.github.kiulian.downloader.model.videos.VideoDetails;
import com.github.kiulian.downloader.model.videos.VideoInfo;
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
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private FxWeaver fxWeaver;

	@Autowired
	private YoutubeVideoDownloadService youtubeVideoDownloadService;

	public void setVideoInfos(String videoId) {
		VideoDetails videoDetails = youtubeVideoDownloadService.getVideoInfo(videoId).details();
		imgThumbnail.setImage(new Image(videoDetails.thumbnails().get(0).split("\\?sqp")[0]));
		txtVideoDescription.setText(videoDetails.description());
		lblVideoTitle.setText(videoDetails.title());
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
