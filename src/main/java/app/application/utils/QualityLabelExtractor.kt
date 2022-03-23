package app.application.utils

import app.application.data.entities.YoutubeVideo
import com.github.kiulian.downloader.model.videos.formats.VideoWithAudioFormat
import org.springframework.stereotype.Service
import java.util.ArrayList

@Service
class QualityLabelExtractor {

    fun getQualityLabels(youtubeVideo: YoutubeVideo): List<String> {
        val audioVideoFormats = youtubeVideo.videoWithAudioFormat
        val qualityLabels: MutableList<String> = ArrayList()
        for (audioVideoFormat in audioVideoFormats) {
            qualityLabels.add(audioVideoFormat.qualityLabel())
        }
        return qualityLabels
    }

}