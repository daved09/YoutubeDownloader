package app.application.controller.main_window;

import app.application.utils.DialogManager;
import app.application.utils.YoutubeIdExtractor;
import app.application.utils.YoutubePlaylistDownloadService;
import app.application.utils.YoutubeUrlValidator;
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

	@Autowired
	private YoutubeUrlValidator youtubeUrlValidator;

	@Autowired
	private DialogManager dialogManager;

	@FXML
	public void initialize(){
		youtubePlaylistDownloadService.setLabel(lblDownloadProgress);
		btnSearchPlaylist.disableProperty().bind(Bindings.isEmpty(txtPlaylistLink.textProperty()));
	}

	public void btnSearchPlaylist_click(){
		if(youtubeUrlValidator.isYoutubeUrlInvalid(txtPlaylistLink.getText())){
			dialogManager.openWarningDialog("Ungültige Url", "Bitte trage eine gültige Url ein.");
			return;
		}
		txtPlaylistTitle.setText(youtubePlaylistDownloadService.getPlaylistInfo(
						youtubeIdExtractor.getPlayListIdFromLink(txtPlaylistLink.getText())).details().title());
		listPlaylist.getItems().addAll(youtubePlaylistDownloadService.getVideoTitles());
		playlistPanel.setVisible(true);
	}

	public void btnPlaylistDownload_click(){
		new Thread(() -> youtubePlaylistDownloadService.downloadPlaylist()).start();
	}
}
