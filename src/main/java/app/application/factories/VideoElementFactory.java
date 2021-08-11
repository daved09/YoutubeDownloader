package app.application.factories;

import app.application.components.VideoElement;
import app.application.data.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Service;

@Service
public class VideoElementFactory {

	@Autowired
	private AutowireCapableBeanFactory autowireCapableBeanFactory;

	public VideoElement createVideoElement(Video video){
		VideoElement videoElement = new VideoElement(video);
		autowireCapableBeanFactory.autowireBean(videoElement);
		return videoElement;
	}

}
