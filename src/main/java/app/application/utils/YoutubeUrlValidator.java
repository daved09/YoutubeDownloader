package app.application.utils;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Service;

@Service
public class YoutubeUrlValidator {

    public boolean isYoutubeUrlInvalid(String url){
        UrlValidator urlValidator = new UrlValidator();
        return !urlValidator.isValid(url) || !isYoutubeUrl(url) || !hasVideoOrPlaylistID(url);
    }

    private boolean isYoutubeUrl(String url){
        return url.contains("youtube.com");
    }

    private boolean hasVideoOrPlaylistID(String url){
        return url.contains("v=") || url.contains("list=");
    }

}
