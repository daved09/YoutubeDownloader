package app.application.config.adapter

import com.google.gson.*
import kotlin.Throws
import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import java.lang.reflect.Type

class BooleanPropertyAdapter : JsonSerializer<BooleanProperty>, JsonDeserializer<BooleanProperty> {
    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): BooleanProperty {
        return SimpleBooleanProperty(json.asJsonPrimitive.asBoolean)
    }

    override fun serialize(src: BooleanProperty, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        return JsonPrimitive(src.value)
    }
}