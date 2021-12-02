package app.application.data.entities;


public abstract class YoutubeEntity <T> {

	protected T reference;

	public T getReference(){
		return this.reference;
	}

}
