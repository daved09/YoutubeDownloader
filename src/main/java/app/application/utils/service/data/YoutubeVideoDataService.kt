package app.application.utils.service.data;

import app.application.data.entities.YoutubeVideo;
import com.github.kiulian.downloader.downloader.request.RequestVideoInfo;
import org.springframework.stereotype.Service;

@Service
public class YoutubeVideoDataService extends YoutubeDataService{

	public YoutubeVideo getYoutubeVideo(String videoId){
		RequestVideoInfo requestVideoInfo = new RequestVideoInfo(videoId);
		return new YoutubeVideo(youtubeDownloader.getVideoInfo(requestVideoInfo).data());
	}

}
