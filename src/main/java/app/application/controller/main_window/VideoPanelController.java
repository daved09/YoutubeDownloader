package app.application.controller.main_window;

import app.application.data.entities.YoutubeVideo;
import app.application.exception.CantAbortDownloadException;
import app.application.exception.ExecutorTerminationException;
import app.application.exception.InvalidVideoUrlException;
import app.application.listener.YoutubeVideoDownloadListener;
import app.application.utils.DialogManager;
import app.application.utils.GlobalValues;
import app.application.utils.QualityLabelExtractor;
import app.application.utils.YoutubeIdExtractor;
import app.application.utils.YoutubeUrlValidator;
import app.application.utils.service.data.YoutubeVideoDataService;
import app.application.utils.service.download.YoutubeVideoDownloadService;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.springframework.stereotype.Component;

import java.util.List;
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

	private final YoutubeVideoDataService youtubeVideoDataService;

	private final YoutubeVideoDownloadService youtubeVideoDownloadService;

	private final YoutubeIdExtractor youtubeIdExtractor;

	private final YoutubeUrlValidator youtubeUrlValidator;

	private final DialogManager dialogManager;

	private final QualityLabelExtractor qualityLabelExtractor;

	public VideoPanelController(
					YoutubeVideoDownloadService youtubeVideoDownloadService,
					YoutubeVideoDataService youtubeVideoDataService,
					YoutubeIdExtractor youtubeIdExtractor,
					YoutubeUrlValidator youtubeUrlValidator,
					DialogManager dialogManager,
					QualityLabelExtractor qualityLabelExtractor) {
		this.youtubeVideoDownloadService = youtubeVideoDownloadService;
		this.youtubeVideoDataService = youtubeVideoDataService;
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


	public void btnSearchClick() throws InvalidVideoUrlException {
//		if(!youtubeUrlValidator.isYoutubeUrlValid(txtDownloadLink.getText())){
//			dialogManager.openWarningDialog("Ungültige Url", "Bitte trage eine gültige Url ein.");
//			return;
//		}
		tmpYoutubeVideo = youtubeVideoDataService.getYoutubeVideo(youtubeIdExtractor.getVideoIdFromLink(txtDownloadLink.getText()));
		imgThumbnail.setImage(new Image(tmpYoutubeVideo.getVideoThumbnailUrl()));
		lblVideoTitle.setText(tmpYoutubeVideo.getVideoTitle());
		refreshQualityBox(qualityLabelExtractor.getQualityLabels(tmpYoutubeVideo));
		txtDescription.setText(tmpYoutubeVideo.getVideoDescription());
		videoPane.setVisible(true);
	}

	public void btnDownloadVideoClick(){
		downloadExecutorService = Executors.newSingleThreadExecutor();
		downloadExecutorService.execute(() -> {
			if(chkAudioOnly.isSelected()){
				youtubeVideoDownloadService.downloadAudioOnlyAsync(tmpYoutubeVideo);
			}
			else{
				youtubeVideoDownloadService.downloadVideoAsync(tmpYoutubeVideo, boxQuality.getSelectionModel().getSelectedItem());
			}
		});
	}

	public void btnAbortClick() throws CantAbortDownloadException {
		downloadExecutorService.shutdownNow();
		try {
			boolean terminationSuccess = downloadExecutorService.awaitTermination(10,
							TimeUnit.SECONDS);
			if(!terminationSuccess){
				throw new ExecutorTerminationException(GlobalValues.DOWNLOAD_EXECUTOR_TERMINATION_ERROR);
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new CantAbortDownloadException(e);
		}
	}


	private void refreshQualityBox(List<String> listWithOptions){
		boxQuality.getItems().clear();
		boxQuality.getItems().addAll(listWithOptions);
		boxQuality.getSelectionModel().select(0);
	}


}
