package app.application.controller.main_window;

import app.application.utils.YoutubeIdExtractor;
import app.application.utils.YoutubePlaylistDownloadService;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlaylistPanelController {

	@FXML
	private AnchorPane playlistPanel;

	@FXML
	private TextField txtPlaylistLink;

	@FXML
	private ListView<String> listPlaylist;

	@FXML
	private Label txtPlaylistTitle;

	@FXML
	private Label lblDownloadProgress;

	@FXML
	private Button btnSearchPlaylist;

	@Autowired
	private YoutubePlaylistDownloadService youtubePlaylistDownloadService;

	@Autowired
	private YoutubeIdExtractor youtubeIdExtractor;


	@FXML
	public void initialize(){
		youtubePlaylistDownloadService.setLabel(lblDownloadProgress);
		btnSearchPlaylist.disableProperty().bind(Bindings.isEmpty(txtPlaylistLink.textProperty()));
	}

	public void btnSearchPlaylist_click(){
		txtPlaylistTitle.setText(youtubePlaylistDownloadService.getPlaylistDetails(
						youtubeIdExtractor.getPlayListIdFromLink(txtPlaylistLink.getText())).title());
		listPlaylist.getItems().addAll(youtubePlaylistDownloadService.getVideoTitles());
		playlistPanel.setVisible(true);
	}

	public void btnPlaylistDownload_click(){
		new Thread(() -> youtubePlaylistDownloadService.downloadPlaylist()).start();
	}
}
