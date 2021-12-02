package app.application.utils.service.data;

import com.github.kiulian.downloader.YoutubeDownloader;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class YoutubeDataService {

	@Autowired
	protected YoutubeDownloader youtubeDownloader;

}
