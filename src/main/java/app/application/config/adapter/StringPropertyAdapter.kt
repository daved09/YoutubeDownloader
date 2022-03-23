package app.application.config.adapter

import com.google.gson.*
import javafx.beans.property.SimpleStringProperty
import kotlin.Throws
import javafx.beans.property.StringProperty
import java.lang.reflect.Type

class StringPropertyAdapter : JsonSerializer<StringProperty>, JsonDeserializer<StringProperty> {
    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): StringProperty {
        return SimpleStringProperty(json.asJsonPrimitive.asString)
    }

    override fun serialize(src: StringProperty, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        return JsonPrimitive(src.value)
    }
}