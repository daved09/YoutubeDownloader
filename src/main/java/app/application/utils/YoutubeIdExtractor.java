package app.application.utils;


import app.application.exception.InvalidPlaylistUrlException;
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
            return youtubeUrlValidator.isShortUrl(videoLink) ? url.getPath().replace("/", "") : parameterMap.get("v");
        } catch (MalformedURLException e) {
            throw new InvalidVideoUrlException(e, videoLink);
        }
    }

    public String getPlayListIdFromLink(String playlistLink) throws InvalidPlaylistUrlException {
        try {
            URL url = new URL(playlistLink);
            Map<String, String> parameterMap = linkParameterBuilder.buildParameterMap(url);
            return parameterMap.getOrDefault("list", "");
        } catch (MalformedURLException e) {
            throw new InvalidPlaylistUrlException(e, playlistLink);
        }
    }

}
