package app.application.controller.main_window;

import app.application.components.VideoElement;
import app.application.data.entities.YoutubePlaylist;
import app.application.factories.VideoElementFactory;
import app.application.utils.DialogManager;
import app.application.utils.YoutubeIdExtractor;
import app.application.utils.service.YoutubePlaylistDownloadService;
import app.application.utils.YoutubeUrlValidator;
import app.application.utils.service.data.YoutubePlaylistDataService;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.springframework.stereotype.Component;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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

	private final YoutubePlaylistDataService youtubePlaylistDataService;

	private final YoutubePlaylistDownloadService youtubePlaylistDownloadService;

	private final YoutubeIdExtractor youtubeIdExtractor;

	private final YoutubeUrlValidator youtubeUrlValidator;

	private final DialogManager dialogManager;

	private final VideoElementFactory videoElementFactory;

	public PlaylistPanelController(
					YoutubePlaylistDownloadService youtubePlaylistDownloadService,
					YoutubePlaylistDataService youtubePlaylistDataService,
					YoutubeIdExtractor youtubeIdExtractor,
					YoutubeUrlValidator youtubeUrlValidator,
					DialogManager dialogManager,
					VideoElementFactory videoElementFactory) {
		this.youtubePlaylistDownloadService = youtubePlaylistDownloadService;
		this.youtubePlaylistDataService = youtubePlaylistDataService;
		this.youtubeIdExtractor = youtubeIdExtractor;
		this.youtubeUrlValidator = youtubeUrlValidator;
		this.dialogManager = dialogManager;
		this.videoElementFactory = videoElementFactory;
	}

	private YoutubePlaylist youtubePlaylist;

	private ExecutorService downloadExecutor;

	@FXML
	public void initialize(){
		youtubePlaylistDownloadService.setLabel(lblDownloadProgress);
		btnSearchPlaylist.disableProperty().bind(Bindings.isEmpty(txtPlaylistLink.textProperty()));
	}

	public void btnSearchPlaylistClick(){
		listPlaylist.getItems().clear();
		if(youtubeUrlValidator.isYoutubeUrlInvalid(txtPlaylistLink.getText())){
			dialogManager.openWarningDialog("Ungültige Url", "Bitte trage eine gültige Url ein.");
			return;
		}
		youtubePlaylist = youtubePlaylistDataService.getPlaylistInfo(youtubeIdExtractor.getPlayListIdFromLink(txtPlaylistLink.getText()));
		lblDownloadProgress.setText("Videos: 0/" + youtubePlaylist.getPlaylistSize());
		txtPlaylistTitle.setText(youtubePlaylist.getPlaylistTitle());
		youtubePlaylist.getPlaylistVideos().forEach(video ->
				listPlaylist.getItems().add(videoElementFactory.createVideoElement(video))
		);
		playlistPanel.setVisible(true);
	}

	public void btnPlaylistDownloadClick(){
		downloadExecutor = Executors.newSingleThreadExecutor();
		downloadExecutor.execute(() -> youtubePlaylistDownloadService.downloadPlaylist(youtubePlaylist));
	}

	public void btnAbortClick(){
		downloadExecutor.shutdownNow();
		try{
			downloadExecutor.awaitTermination(1, TimeUnit.SECONDS);//TODO: siehe VideoPanelController
		}
		catch (CancellationException | InterruptedException ignored){}
	}

}
