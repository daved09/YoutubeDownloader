package app.application.utils;

import app.application.data.entities.YoutubeVideo;
import com.github.kiulian.downloader.model.videos.VideoInfo;
import com.github.kiulian.downloader.model.videos.formats.VideoWithAudioFormat;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QualityLabelExtractor {

	public List<String> getQualityLabels(YoutubeVideo youtubeVideo) {
		List<VideoWithAudioFormat> audioVideoFormats = youtubeVideo.getVideoWithAudioFormat();
		List<String> qualityLabels = new ArrayList<>();
		for (VideoWithAudioFormat audioVideoFormat : audioVideoFormats) {
			qualityLabels.add(audioVideoFormat.qualityLabel());
		}
		return qualityLabels;
	}

}
