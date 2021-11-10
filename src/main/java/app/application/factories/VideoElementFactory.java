package app.application.factories;

import app.application.components.VideoElement;
import app.application.data.entities.YoutubePlaylistVideoDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Service;

@Service
public class VideoElementFactory {

	@Autowired
	private AutowireCapableBeanFactory autowireCapableBeanFactory;

	public VideoElement createVideoElement(YoutubePlaylistVideoDetail youtubePlaylistVideoDetail){
		VideoElement videoElement = new VideoElement();
		autowireCapableBeanFactory.autowireBean(videoElement);
		videoElement.loadVideoDetails(youtubePlaylistVideoDetail);
		return videoElement;
	}

}
