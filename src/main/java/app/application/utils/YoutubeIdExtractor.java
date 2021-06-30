package app.application.utils;


import org.springframework.stereotype.Service;

@Service
public class YoutubeIdExtractor {

    public String getVideoIdFromLink(String videoLink){
        videoLink = videoLink.split("&t=")[0];
        return videoLink.contains("v=") ? videoLink.split("v=")[1] : "";
    }

    public String getPlayListIdFromLink(String playlistLink){
        return playlistLink.contains("list=") ? playlistLink.split("list=")[1] : "";
    }

}
