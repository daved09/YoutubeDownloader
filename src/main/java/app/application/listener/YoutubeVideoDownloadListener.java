package app.application.listener;

import app.application.utils.DialogManager;
import javafx.application.Platform;
import javafx.scene.control.ProgressBar;

import java.io.File;

public class YoutubeVideoDownloadListener extends YoutubeDownloadListener {

    private final ProgressBar progressBar;

    public YoutubeVideoDownloadListener(ProgressBar progressBar, DialogManager dialogManager) {
        super(dialogManager);
        this.progressBar = progressBar;
    }

    @Override
    public void onFinished(File data) {
        Platform.runLater(() -> dialogManager.openInformationDialog("Download fertig.", ""));
    }

    @Override
    public void onDownloading(int progress) {
        Platform.runLater(() -> progressBar.setProgress(Double.parseDouble(Integer.toString(progress)) / 100));
    }

}
