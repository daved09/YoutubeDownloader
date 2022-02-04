package app.application.utils;


import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

@Service
public class YoutubeIdExtractor {

    private YoutubeUrlValidator youtubeUrlValidator;
    private LinkParameterBuilder linkParameterBuilder;
    private DialogManager dialogManager;

    public YoutubeIdExtractor(YoutubeUrlValidator youtubeUrlValidator, LinkParameterBuilder linkParameterBuilder, DialogManager dialogManager) {
        this.youtubeUrlValidator = youtubeUrlValidator;
        this.linkParameterBuilder = linkParameterBuilder;
        this.dialogManager = dialogManager;
    }

    public String getVideoIdFromLink(String videoLink){
        try {
            URL url = new URL(videoLink);
            Map<String, String> parameterMap = linkParameterBuilder.buildParameterMap(url);
            return youtubeUrlValidator.isShortUrl(videoLink) ? url.getPath().replaceAll("/", "") : parameterMap.get("v");
        } catch (MalformedURLException e) {
            dialogManager.openErrorDialog("Ungültige Url", "Die eingegebene Url ist ungültig.");
        }
        return null;
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
