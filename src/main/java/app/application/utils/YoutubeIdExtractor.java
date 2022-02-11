package app.application.utils;


import app.application.exception.InvalidVideoUrlException;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

@Service
public class YoutubeIdExtractor {

    private final YoutubeUrlValidator youtubeUrlValidator;
    private final LinkParameterBuilder linkParameterBuilder;
    private final DialogManager dialogManager;

    public YoutubeIdExtractor(YoutubeUrlValidator youtubeUrlValidator, LinkParameterBuilder linkParameterBuilder, DialogManager dialogManager) {
        this.youtubeUrlValidator = youtubeUrlValidator;
        this.linkParameterBuilder = linkParameterBuilder;
        this.dialogManager = dialogManager;
    }

    public String getVideoIdFromLink(String videoLink) throws InvalidVideoUrlException {
        try {
            URL url = new URL(videoLink);
            Map<String, String> parameterMap = linkParameterBuilder.buildParameterMap(url);
            return youtubeUrlValidator.isShortUrl(videoLink) ? url.getPath().replaceAll("/", "") : parameterMap.get("v");
        } catch (MalformedURLException e) {
            throw new InvalidVideoUrlException(e, videoLink);
        }
    }

    public String getPlayListIdFromLink(String playlistLink){
        return playlistLink.contains("list=") ? playlistLink.split("list=")[1] : "";
    }

}
