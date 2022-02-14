package app.application.data.entities;

import app.application.exception.VideoInformationNotAvailable;

import java.util.Optional;

public abstract class YoutubeEntity <T> {

	protected Optional<T> reference;

	public T getReference(){
		return reference.orElseThrow(VideoInformationNotAvailable::new);
	}

}
