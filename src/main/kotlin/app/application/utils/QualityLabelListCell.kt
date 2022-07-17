package app.application.utils

import com.github.kiulian.downloader.model.videos.formats.VideoWithAudioFormat
import javafx.scene.control.ListCell

class QualityLabelListCell: ListCell<VideoWithAudioFormat>() {

    override fun updateItem(item: VideoWithAudioFormat?, empty: Boolean) {
        super.updateItem(item, empty)
        text = if(empty) "" else item?.qualityLabel()
    }


}