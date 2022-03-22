package app.application.data.entities;

import com.github.kiulian.downloader.model.videos.VideoDetails;
import com.github.kiulian.downloader.model.videos.VideoInfo;
import com.github.kiulian.downloader.model.videos.formats.AudioFormat;
import com.github.kiulian.downloader.model.videos.formats.Format;
import com.github.kiulian.downloader.model.videos.formats.VideoWithAudioFormat;

import java.util.List;
import java.util.Optional;

public class YoutubeVideo extends YoutubeEntity<VideoInfo>{

	public YoutubeVideo(VideoInfo videoInfo){
		this.reference = Optional.ofNullable(videoInfo);
	}

	public String getVideoId(){
		return getVideoDetails().videoId();
	}

	public String getVideoDescription(){
		return getVideoDetails().description();
	}

	public String getVideoTitle(){
		return getVideoDetails().title();
	}

	public String getVideoThumbnailUrl(){
		return getVideoDetails().thumbnails().get(0).split("\\?sqp")[0];
	}

	public VideoDetails getVideoDetails(){
		return getReference().details();
	}

	public List<VideoWithAudioFormat> getVideoWithAudioFormat(){
		return getReference().videoWithAudioFormats();
	}

	public AudioFormat getAudioFormat(){
		return getReference().audioFormats().get(0);
	}

	public Format getBestVideoWithAudioFormat() {
		return getReference().bestVideoWithAudioFormat();
	}
}
