package app.application.utils;

import com.github.kiulian.downloader.OnYoutubeDownloadListener;
import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import lombok.AllArgsConstructor;

import java.io.File;

@AllArgsConstructor
public class YoutubeDownloadListener implements OnYoutubeDownloadListener {

    private ProgressBar progressBar;

    @Override
    public void onDownloading(int progress) {
        Platform.runLater(() -> progressBar.setProgress(Double.parseDouble(Integer.toString(progress)) / 100));
    }

    @Override
    public void onFinished(File file) {

    }

    @Override
    public void onError(Throwable throwable) {

    }
}
