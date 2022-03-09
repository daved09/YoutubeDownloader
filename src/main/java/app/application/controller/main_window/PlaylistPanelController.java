package app.application.controller.main_window;

import app.application.components.VideoElement;
import app.application.data.entities.YoutubePlaylist;
import app.application.exception.CantAbortDownloadException;
import app.application.exception.InvalidPlaylistUrlException;
import app.application.factories.VideoElementFactory;
import app.application.utils.DownloadExecutorHandler;
import app.application.utils.YoutubeIdExtractor;
import app.application.utils.YoutubeUrlValidator;
import app.application.utils.service.data.YoutubePlaylistDataService;
import app.application.utils.service.download.YoutubePlaylistDownloadService;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.springframework.stereotype.Component;

@Component
public class PlaylistPanelController {

	@FXML
	private AnchorPane playlistPanel;

	@FXML
	private TextField txtPlaylistLink;

	@FXML
	private ListView<VideoElement> listPlaylist;

	@FXML
	private Label txtPlaylistTitle;

	@FXML
	private Label lblDownloadProgress;

	@FXML
	private Button btnSearchPlaylist;

	@FXML
	private CheckBox chkAudioOnly;

	private final YoutubePlaylistDataService youtubePlaylistDataService;

	private final YoutubePlaylistDownloadService youtubePlaylistDownloadService;

	private final YoutubeIdExtractor youtubeIdExtractor;

	private final YoutubeUrlValidator youtubeUrlValidator;

	private final VideoElementFactory videoElementFactory;

	private DownloadExecutorHandler downloadExecutorHandler;

	public PlaylistPanelController(
					YoutubePlaylistDownloadService youtubePlaylistDownloadService,
					YoutubePlaylistDataService youtubePlaylistDataService,
					YoutubeIdExtractor youtubeIdExtractor,
					YoutubeUrlValidator youtubeUrlValidator,
					VideoElementFactory videoElementFactory) {
		this.youtubePlaylistDownloadService = youtubePlaylistDownloadService;
		this.youtubePlaylistDataService = youtubePlaylistDataService;
		this.youtubeIdExtractor = youtubeIdExtractor;
		this.youtubeUrlValidator = youtubeUrlValidator;
		this.videoElementFactory = videoElementFactory;
	}

	private YoutubePlaylist youtubePlaylist;

	@FXML
	public void initialize(){
		youtubePlaylistDownloadService.setLabel(lblDownloadProgress);
		btnSearchPlaylist.disableProperty().bind(Bindings.isEmpty(txtPlaylistLink.textProperty()));
		downloadExecutorHandler = new DownloadExecutorHandler();
	}

	public void btnSearchPlaylistClick() throws InvalidPlaylistUrlException {
		listPlaylist.getItems().clear();
		youtubeUrlValidator.checkPlaylistUrl(txtPlaylistLink.getText());
		youtubePlaylist = youtubePlaylistDataService.getPlaylistInfo(youtubeIdExtractor.getPlayListIdFromLink(txtPlaylistLink.getText()));
		lblDownloadProgress.setText("Videos: 0/" + youtubePlaylist.getPlaylistSize());
		txtPlaylistTitle.setText(youtubePlaylist.getPlaylistTitle());
		youtubePlaylist.getPlaylistVideos().forEach(video ->
				listPlaylist.getItems().add(videoElementFactory.createVideoElement(video))
		);
		chkAudioOnly.selectedProperty().bindBidirectional(youtubePlaylist.getAudioOnly());
		playlistPanel.setVisible(true);
	}

	public void btnPlaylistDownloadClick(){
		downloadExecutorHandler.executeTask(() -> youtubePlaylistDownloadService.downloadPlaylist(youtubePlaylist));
	}

	public void btnAbortClick() throws CantAbortDownloadException {
		downloadExecutorHandler.killTask();
	}

}
