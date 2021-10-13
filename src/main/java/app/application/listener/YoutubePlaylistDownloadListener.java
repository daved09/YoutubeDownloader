package app.application.listener;

import app.application.utils.DialogManager;
import javafx.application.Platform;

import java.io.File;

public class YoutubePlaylistDownloadListener extends YoutubeDownloadListener{

    private final int videoAmount;

    private int actualVideo = 0;

    public YoutubePlaylistDownloadListener(DialogManager dialogManager, int videoAmount) {
        super(dialogManager);
        this.videoAmount = videoAmount;
    }

    @Override
    public void onDownloading(int progress) {
    }

    @Override
    public void onFinished(File data) {
        actualVideo ++;
        if(actualVideo == videoAmount){
            Platform.runLater(() -> dialogManager.openInformationDialog("Download fertig.", ""));
        }
    }

    @Override
    public boolean isDownloadFinished() {
        return false;
    }
}
