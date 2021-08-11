package app.application.factories;

import app.application.data.Video;
import com.github.kiulian.downloader.model.playlist.PlaylistVideoDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VideoListFactory {

    public List<Video> createVideoList(List<PlaylistVideoDetails> playlistVideoDetailsList){
        List<Video> videoList = new ArrayList<>();
        playlistVideoDetailsList.forEach(playlistVideoDetails -> {
            videoList.add(new Video(playlistVideoDetails));
        });
        return videoList;
    }

}
