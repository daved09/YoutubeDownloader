package app.application.listener;

import app.application.utils.DialogManager;
import app.application.utils.GlobalObjectHandler;
import javafx.application.Platform;

import java.io.File;

public class YoutubePlaylistDownloadListener extends YoutubeDownloadListener{

    private final int videoAmount;

    private final GlobalObjectHandler globalObjectHandler;

    private int actualVideo = 0;

    public YoutubePlaylistDownloadListener(DialogManager dialogManager, int videoAmount, GlobalObjectHandler globalObjectHandler) {
        super(dialogManager);
        this.videoAmount = videoAmount;
        this.globalObjectHandler = globalObjectHandler;
    }

    @Override
    public void onDownloading(int progress) {
        // Wird nicht verwendet
    }

    @Override
    public void onFinished(File data) {
        actualVideo ++;
        if(actualVideo == videoAmount){
            globalObjectHandler.getHostServices().showDocument(data.getParent());
            Platform.runLater(() -> dialogManager.openInformationDialog("Download fertig.", ""));
        }
    }

    @Override
    public boolean isDownloadFinished() {
        return false;
    }
}
