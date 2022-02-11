package app.application.utils;

import app.application.exception.InvalidPlaylistUrlException;
import app.application.exception.InvalidVideoUrlException;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Service;

@Service
public class YoutubeUrlValidator {

    private final UrlValidator urlValidator = new UrlValidator();

    public void checkVideoUrl(String url) throws InvalidVideoUrlException {
        if(!urlValidator.isValid(url) && !isYoutubeUrl(url) && !hasVideoID(url)){
            throw new InvalidVideoUrlException(url);
        }
    }

    public void checkPlaylistUrl(String url) throws InvalidPlaylistUrlException {
        if(!urlValidator.isValid(url) && !hasPlaylistID(url)){
            throw new InvalidPlaylistUrlException(url);
        }
    }

    public boolean isShortUrl(String url){
        return url.contains(GlobalValues.YOUTUBE_SHORT_URL);
    }

    private boolean isYoutubeUrl(String url){
        return url.contains(GlobalValues.YOUTUBE_URL) || isShortUrl(url);
    }

    private boolean hasVideoID(String url){
        return url.contains("v=") || isShortUrl(url);
    }

    private boolean hasPlaylistID(String url){
        return url.contains("list=");
    }

}
