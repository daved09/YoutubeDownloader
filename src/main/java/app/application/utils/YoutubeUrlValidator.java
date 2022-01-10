package app.application.utils;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Service;

@Service
public class YoutubeUrlValidator {

    private final UrlValidator urlValidator = new UrlValidator();

    public boolean isYoutubeUrlValid(String url){
        return urlValidator.isValid(url) || isYoutubeUrl(url) || hasVideoOrPlaylistID(url);
    }

    public boolean isShortUrl(String url){
        return url.contains(GlobalValues.YOUTUBE_SHORT_URL);
    }

    private boolean isYoutubeUrl(String url){
        return url.contains(GlobalValues.YOUTUBE_URL) || isShortUrl(url);
    }

    private boolean hasVideoOrPlaylistID(String url){
        return url.contains("v=") || url.contains("list=") || isShortUrl(url);
    }

}
