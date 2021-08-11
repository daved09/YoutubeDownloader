package app.application.components;

import app.application.controller.VideoDetailsController;
import app.application.data.Video;
import app.application.utils.ComponentUtils;
import app.application.utils.YoutubeVideoDownloadService;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lombok.Setter;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Autowired;

public class VideoElement extends AnchorPane {

	@FXML
	private ImageView imgThumbnail;

	@FXML
	private Label lblVideoTitle;

	@FXML
	private CheckBox chkIgnore;

	@Autowired
	private YoutubeVideoDownloadService youtubeVideoDownloadService;

	@Autowired
	private FxWeaver fxWeaver;

	@Autowired
	private VideoDetailsController videoDetailsController;

	@Setter
	private Video video;

	public VideoElement(Video video) {
		ComponentUtils.loadComponent(this);
		loadVideoDetails(video);
	}

	private void loadVideoDetails(Video video){
		setVideo(video);
		imgThumbnail.setImage(new Image(video.getPlaylistVideoDetails().thumbnails().get(0).split("\\?sqp")[0]));
		lblVideoTitle.setText(video.getPlaylistVideoDetails().title());
		chkIgnore.selectedProperty().bindBidirectional(video.getIgnore());
	}

	public void mouseClicked(MouseEvent event){
		if(event.getClickCount() != 2){
			return;
		}
		videoDetailsController.open();
		videoDetailsController.setVideoInfos(video.getPlaylistVideoDetails().videoId());
	}


}
