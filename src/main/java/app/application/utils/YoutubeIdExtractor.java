package app.application.utils;


import org.springframework.stereotype.Service;

@Service
public class YoutubeIdExtractor {

    private YoutubeUrlValidator youtubeUrlValidator;

    public YoutubeIdExtractor(YoutubeUrlValidator youtubeUrlValidator) {
        this.youtubeUrlValidator = youtubeUrlValidator;
    }

    public String getVideoIdFromLink(String videoLink){
        videoLink = removeTimeStamp(videoLink);
        if(youtubeUrlValidator.isShortUrl(videoLink)){
            return videoLink.substring(videoLink.lastIndexOf("/") + 1);
        }
        return videoLink.contains("v=") ? videoLink.split("v=")[1] : "";
    }

    private String removeTimeStamp(String videoLink) {
        videoLink = videoLink.split("t=")[0];
        if(videoLink.endsWith("&")){
            videoLink = videoLink.substring(0, videoLink.lastIndexOf("&"));
        }
        if(videoLink.endsWith("?")){
            videoLink = videoLink.substring(0, videoLink.lastIndexOf("?"));
        }
        return videoLink;
    }

    public String getPlayListIdFromLink(String playlistLink){
        return playlistLink.contains("list=") ? playlistLink.split("list=")[1] : "";
    }

}
