package app.application.controller.main_window;

import app.application.data.entities.YoutubeVideo;
import app.application.listener.YoutubeVideoDownloadListener;
import app.application.utils.*;
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

	private final YoutubeVideoDownloadService youtubeVideoDownloadService;

	private final YoutubeIdExtractor youtubeIdExtractor;

	private final YoutubeUrlValidator youtubeUrlValidator;

	private final DialogManager dialogManager;

	private final QualityLabelExtractor qualityLabelExtractor;

	@Autowired
	public VideoPanelController(
					YoutubeVideoDownloadService youtubeVideoDownloadService,
					YoutubeIdExtractor youtubeIdExtractor,
					YoutubeUrlValidator youtubeUrlValidator,
					DialogManager dialogManager,
					QualityLabelExtractor qualityLabelExtractor) {
		this.youtubeVideoDownloadService = youtubeVideoDownloadService;
		this.youtubeIdExtractor = youtubeIdExtractor;
		this.youtubeUrlValidator = youtubeUrlValidator;
		this.dialogManager = dialogManager;
		this.qualityLabelExtractor = qualityLabelExtractor;
	}

	private YoutubeVideo tmpYoutubeVideo;

	private ExecutorService downloadExecutorService;

	@FXML
	private void initialize(){
		youtubeVideoDownloadService.setYoutubeDownloadListener(new YoutubeVideoDownloadListener(downloadProgress, dialogManager));
		btnSearch.disableProperty().bind(Bindings.isEmpty(txtDownloadLink.textProperty()));
	}


	public void btnSearch_click(){
		if(youtubeUrlValidator.isYoutubeUrlInvalid(txtDownloadLink.getText())){
			dialogManager.openWarningDialog("Ungültige Url", "Bitte trage eine gültige Url ein.");
			return;
		}
		tmpYoutubeVideo = youtubeVideoDownloadService.getVideoInfo(youtubeIdExtractor.getVideoIdFromLink(txtDownloadLink.getText()));
		imgThumbnail.setImage(new Image(tmpYoutubeVideo.getVideoThumbnailUrl()));
		lblVideoTitle.setText(tmpYoutubeVideo.getVideoTitle());
		refreshQualityBox(qualityLabelExtractor.getQualityLabels(tmpYoutubeVideo));
		txtDescription.setText(tmpYoutubeVideo.getVideoDescription());
		videoPane.setVisible(true);
	}

	public void btnDownloadVideo_click(){
		downloadExecutorService = Executors.newSingleThreadExecutor();
		downloadExecutorService.execute(new Thread(() -> {
			if(chkAudioOnly.isSelected()){
				youtubeVideoDownloadService.downloadAudioOnlyAsync(tmpYoutubeVideo);
			}
			else{
				youtubeVideoDownloadService.downloadVideoAsync(tmpYoutubeVideo, boxQuality.getSelectionModel().getSelectedItem());
			}
		}));
	}

	public void btnAbort_click(){
		downloadExecutorService.shutdownNow();
		try {
			downloadExecutorService.awaitTermination(1, TimeUnit.SECONDS);
		} catch (CancellationException | InterruptedException ignored) {}
		youtubeVideoDownloadService.deleteUnfinishedDownload(tmpYoutubeVideo);
	}


	private void refreshQualityBox(List<String> listWithOptions){
		boxQuality.getItems().clear();
		boxQuality.getItems().addAll(listWithOptions);
		boxQuality.getSelectionModel().select(0);
	}


}
