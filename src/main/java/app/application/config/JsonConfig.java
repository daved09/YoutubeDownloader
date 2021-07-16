package app.application.config;

import app.application.config.adapter.BooleanPropertyAdapter;
import app.application.config.adapter.StringPropertyAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonConfig {


	@Bean
	public Gson gsonConfigurator(){
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setPrettyPrinting();
		gsonBuilder.registerTypeAdapter(StringProperty.class, new StringPropertyAdapter());
		gsonBuilder.registerTypeAdapter(BooleanProperty.class, new BooleanPropertyAdapter());
		return gsonBuilder.create();
	}

}
