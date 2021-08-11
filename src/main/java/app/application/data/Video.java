package app.application.data;

import com.github.kiulian.downloader.model.playlist.PlaylistVideoDetails;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.Data;

@Data
public class Video {

    private PlaylistVideoDetails playlistVideoDetails;
    private BooleanProperty ignore;

    public Video(PlaylistVideoDetails playlistVideoDetails) {
        this.playlistVideoDetails = playlistVideoDetails;
        this.ignore = new SimpleBooleanProperty(false);
    }
}
