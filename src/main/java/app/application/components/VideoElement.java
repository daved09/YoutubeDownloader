package app.application.components;

import app.application.controller.VideoDetailsController;
import app.application.data.entities.YoutubePlaylistVideoDetail;
import app.application.utils.ComponentUtils;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

public class VideoElement extends AnchorPane {

	@FXML
	private ImageView imgThumbnail;

	@FXML
	private Label lblVideoTitle;

	@FXML
	private CheckBox chkIgnore;

	@Autowired
	private VideoDetailsController videoDetailsController;

	private YoutubePlaylistVideoDetail youtubePlaylistVideoDetail;

	public VideoElement() {
		ComponentUtils.loadComponent(this);
	}

	public void loadVideoDetails(YoutubePlaylistVideoDetail youtubePlaylistVideoDetail){
		this.youtubePlaylistVideoDetail = youtubePlaylistVideoDetail;
		imgThumbnail.setImage(new Image(youtubePlaylistVideoDetail.getVideoThumbnailUrl()));
		lblVideoTitle.setText(youtubePlaylistVideoDetail.getVideoTitle());
		chkIgnore.selectedProperty().bindBidirectional(youtubePlaylistVideoDetail.getIgnore());
	}

	public void mouseClicked(MouseEvent event){
		if(event.getClickCount() != 2){
			return;
		}
		videoDetailsController.open();
		videoDetailsController.setVideoInfos(youtubePlaylistVideoDetail.getVideoId());
	}


}
