package app.application.factories;

import app.application.components.VideoElement;
import com.github.kiulian.downloader.model.playlist.PlaylistVideoDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Service;

@Service
public class VideoElementFactory {

	@Autowired
	private AutowireCapableBeanFactory autowireCapableBeanFactory;

	public VideoElement createVideoElement(PlaylistVideoDetails playlistVideoDetails){
		VideoElement videoElement = new VideoElement(playlistVideoDetails);
		autowireCapableBeanFactory.autowireBean(videoElement);
		return videoElement;
	}

}
