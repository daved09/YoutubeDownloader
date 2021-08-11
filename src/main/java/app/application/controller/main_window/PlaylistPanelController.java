package app.application.controller.main_window;

import app.application.components.VideoElement;
import app.application.data.Video;
import app.application.factories.VideoElementFactory;
import app.application.factories.VideoListFactory;
import app.application.utils.DialogManager;
import app.application.utils.YoutubeIdExtractor;
import app.application.utils.YoutubePlaylistDownloadService;
import app.application.utils.YoutubeUrlValidator;
import com.github.kiulian.downloader.model.playlist.PlaylistInfo;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
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


	@Autowired
	private YoutubePlaylistDownloadService youtubePlaylistDownloadService;

	@Autowired
	private YoutubeIdExtractor youtubeIdExtractor;

	@Autowired
	private YoutubeUrlValidator youtubeUrlValidator;

	@Autowired
	private DialogManager dialogManager;

	@Autowired
	private VideoElementFactory videoElementFactory;

	@Autowired
	private VideoListFactory videoListFactory;

	private List<Video> videoList;

	private PlaylistInfo playlistInfo;

	private ExecutorService downloadExecutor;

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
		playlistInfo = youtubePlaylistDownloadService.getPlaylistInfo(youtubeIdExtractor.getPlayListIdFromLink(txtPlaylistLink.getText()));
		txtPlaylistTitle.setText(playlistInfo.details().title());
		this.videoList = videoListFactory.createVideoList(playlistInfo.videos());
		videoList.forEach(video ->
				listPlaylist.getItems().add(videoElementFactory.createVideoElement(video))
		);
//		playlistInfo.videos().forEach(playlistVideoDetails ->
//						listPlaylist.getItems().add(videoElementFactory.createVideoElement(playlistVideoDetails))
//		);
		playlistPanel.setVisible(true);
	}

	public void btnPlaylistDownload_click(){
		downloadExecutor = Executors.newSingleThreadExecutor();
		downloadExecutor.execute(new Thread(() -> youtubePlaylistDownloadService.downloadPlaylist(playlistInfo.details().title(), videoList)));
	}

	public void btnAbort_click(){
		downloadExecutor.shutdownNow();
		try{
			downloadExecutor.awaitTermination(1, TimeUnit.SECONDS);
		}
		catch (CancellationException | InterruptedException ignored){}
	}

}
