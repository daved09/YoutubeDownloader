package app.application.components;

import app.application.controller.VideoDetailsController;
import app.application.utils.ComponentUtils;
import app.application.utils.YoutubeVideoDownloadService;
import com.github.kiulian.downloader.model.playlist.PlaylistVideoDetails;
import javafx.fxml.FXML;
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

	@Autowired
	private YoutubeVideoDownloadService youtubeVideoDownloadService;

	@Autowired
	private FxWeaver fxWeaver;

	@Autowired
	private VideoDetailsController videoDetailsController;

	@Setter
	private PlaylistVideoDetails playlistVideoDetails;

	public VideoElement(PlaylistVideoDetails playlistVideoDetails) {
		ComponentUtils.loadComponent(this);
		setVideo(playlistVideoDetails);
	}

	private void setVideo(PlaylistVideoDetails playlistVideoDetails){
		setPlaylistVideoDetails(playlistVideoDetails);
		imgThumbnail.setImage(new Image(playlistVideoDetails.thumbnails().get(0).split("\\?sqp")[0]));
		lblVideoTitle.setText(playlistVideoDetails.title());
	}

	public void mouseClicked(MouseEvent event){
		if(event.getClickCount() != 2){
			return;
		}
		videoDetailsController.open();
		videoDetailsController.setVideoInfos(playlistVideoDetails.videoId());
	}


}
