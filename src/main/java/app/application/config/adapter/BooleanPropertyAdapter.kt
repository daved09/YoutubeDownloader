package app.application.config.adapter;

import com.google.gson.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.lang.reflect.Type;

public class BooleanPropertyAdapter implements JsonSerializer<BooleanProperty>, JsonDeserializer<BooleanProperty> {

    @Override
    public BooleanProperty deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return new SimpleBooleanProperty(json.getAsJsonPrimitive().getAsBoolean());
    }

    @Override
    public JsonElement serialize(BooleanProperty src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getValue());
    }
}
