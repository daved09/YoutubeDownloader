package app.application.config.adapter;

import com.google.gson.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.lang.reflect.Type;

public class StringPropertyAdapter implements JsonSerializer<StringProperty>, JsonDeserializer<StringProperty> {

	@Override
	public StringProperty deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
					throws JsonParseException {
		return new SimpleStringProperty(json.getAsJsonPrimitive().getAsString());
	}

	@Override
	public JsonElement serialize(StringProperty src, Type typeOfSrc, JsonSerializationContext context) {
		return new JsonPrimitive(src.getValue());
	}

}
