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

    public YoutubeIdExtractor(YoutubeUrlValidator youtubeUrlValidator, LinkParameterBuilder linkParameterBuilder) {
        this.youtubeUrlValidator = youtubeUrlValidator;
        this.linkParameterBuilder = linkParameterBuilder;
    }

    public String getVideoIdFromLink(String videoLink) throws InvalidVideoUrlException {
        try {
            URL url = new URL(videoLink);
            Map<String, String> parameterMap = linkParameterBuilder.buildParameterMap(url.getQuery());
            return youtubeUrlValidator.isShortUrl(url.getHost()) ? url.getPath().substring(1) : parameterMap.get("v");
        } catch (MalformedURLException e) {
            throw new InvalidVideoUrlException(e, videoLink);
        }
    }

    public String getPlayListIdFromLink(String playlistLink) throws InvalidPlaylistUrlException {
        try {
            URL url = new URL(playlistLink);
            Map<String, String> parameterMap = linkParameterBuilder.buildParameterMap(url.getQuery());
            return parameterMap.getOrDefault("list", "");
        } catch (MalformedURLException e) {
            throw new InvalidPlaylistUrlException(e, playlistLink);
        }
    }

}
