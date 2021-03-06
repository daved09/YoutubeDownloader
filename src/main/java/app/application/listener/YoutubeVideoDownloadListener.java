package app.application.listener;

import app.application.utils.DialogManager;
import app.application.utils.GlobalObjectHandler;
import javafx.application.Platform;
import javafx.scene.control.ProgressBar;

import java.io.File;

public class YoutubeVideoDownloadListener extends YoutubeDownloadListener {

    public static final int FINISHED_PROGRESS = 100;
    private final ProgressBar progressBar;
    private final GlobalObjectHandler globalObjectHandler;

    private int currentProgress;

    public YoutubeVideoDownloadListener(ProgressBar progressBar, DialogManager dialogManager, GlobalObjectHandler globalObjectHandler) {
        super(dialogManager);
        this.progressBar = progressBar;
        this.globalObjectHandler = globalObjectHandler;
    }

    @Override
    public void onFinished(File data) {
        globalObjectHandler.getHostServices().showDocument(data.getParent());
        Platform.runLater(() -> dialogManager.openInformationDialog("Download fertig.", ""));
    }

    @Override
    public void onDownloading(int progress) {
        this.currentProgress = progress;
        Platform.runLater(() -> progressBar.setProgress(Double.parseDouble(Integer.toString(progress)) / 100));
    }

    @Override
    public boolean isDownloadFinished() {
        return currentProgress >= FINISHED_PROGRESS;
    }

}
