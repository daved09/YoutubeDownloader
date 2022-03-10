package app.application.listener;

import app.application.utils.DialogManager;
import com.github.kiulian.downloader.downloader.YoutubeProgressCallback;
import lombok.AllArgsConstructor;

import java.io.File;

@AllArgsConstructor
public abstract class YoutubeDownloadListener implements YoutubeProgressCallback<File> {

    protected final DialogManager dialogManager;

    @Override
    public void onError(Throwable throwable) {

    }

    public abstract boolean isDownloadFinished();

}
