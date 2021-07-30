package app.application.controller.main_window;

import app.application.utils.*;
import com.github.kiulian.downloader.model.videos.VideoInfo;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class VideoPanelController {

	@FXML
	private TextField txtDownloadLink;

	@FXML
	private ImageView imgThumbnail;

	@FXML
	private AnchorPane videoPane;

	@FXML
	private Label lblVideoTitle;

	@FXML
	private ComboBox<String> boxQuality;

	@FXML
	private TextArea txtDescription;

	@FXML
	private ProgressBar downloadProgress;

	@FXML
	private CheckBox chkAudioOnly;

	@FXML
	private Button btnSearch;

	@Autowired
	private YoutubeVideoDownloadService youtubeVideoDownloadService;

	@Autowired
	private YoutubeIdExtractor youtubeIdExtractor;

	@Autowired
	private YoutubeUrlValidator youtubeUrlValidator;

	@Autowired
	private DialogManager dialogManager;

	private VideoInfo tmpVideoInfo;

	private ExecutorService downloadExecutorService;

	@FXML
	private void initialize(){
		youtubeVideoDownloadService.setYoutubeDownloadListener(new YoutubeDownloadListener(downloadProgress));
		btnSearch.disableProperty().bind(Bindings.isEmpty(txtDownloadLink.textProperty()));
	}


	public void btnSearch_click(){
		if(youtubeUrlValidator.isYoutubeUrlInvalid(txtDownloadLink.getText())){
			dialogManager.openWarningDialog("Ungültige Url", "Bitte trage eine gültige Url ein.");
			return;
		}
		tmpVideoInfo = youtubeVideoDownloadService.getVideoInfo(youtubeIdExtractor.getVideoIdFromLink(txtDownloadLink.getText()));
		imgThumbnail.setImage(new Image(tmpVideoInfo.details().thumbnails().get(0).split("\\?sqp")[0]));
		lblVideoTitle.setText(tmpVideoInfo.details().title());
		refreshQualityBox(youtubeVideoDownloadService.getQualityLabels(tmpVideoInfo));
		txtDescription.setText(tmpVideoInfo.details().description());
		videoPane.setVisible(true);
	}

	public void btnDownloadVideo_click(){
		downloadExecutorService = Executors.newSingleThreadExecutor();
		downloadExecutorService.execute(new Thread(() -> {
			if(chkAudioOnly.isSelected()){
				youtubeVideoDownloadService.downloadAudioOnlyAsync(tmpVideoInfo);
			}
			else{
				youtubeVideoDownloadService.downloadVideoAsync(tmpVideoInfo, boxQuality.getSelectionModel().getSelectedItem());
			}
		}));
	}

	public void btnAbord_click(){
		downloadExecutorService.shutdownNow();
		try {
			downloadExecutorService.awaitTermination(1, TimeUnit.SECONDS);
		} catch (CancellationException | InterruptedException ignored) {}
	}


	private void refreshQualityBox(List<String> listWithOptions){
		boxQuality.getItems().clear();
		boxQuality.getItems().addAll(listWithOptions);
		boxQuality.getSelectionModel().select(0);
	}


}
