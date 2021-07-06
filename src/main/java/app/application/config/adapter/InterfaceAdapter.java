package app.application.config.adapter;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 *
 * in our program we have a HashMap with SingleObjectRoutePointHandler this class is extended by many classes like MapperHandler.
 * so this class will specified the type of the class is serialize in a string (ex: {"type":"de.abas.connect.workflow.route_modules.mapper.MapperHandler","data":{}})
 * because if we dont do this when we will deserialize our json the program will try to create an object of the type SingleObjectRoutePointHandler because that the type of the HashMap
 * but the problem is that we serialize a MapperHandler and that why we use this class.
 *
 * @author gseydoux
 * 
 */

public final class InterfaceAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {
	@Override
	public JsonElement serialize(T object, Type interfaceType, JsonSerializationContext context) {
        final JsonObject wrapper = new JsonObject();
        wrapper.addProperty("type", object.getClass().getName());
        wrapper.add("data", context.serialize(object));
        return wrapper;
    }
    
    @Override
    public T deserialize(JsonElement elem, Type interfaceType, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject wrapper = (JsonObject) elem;
        final JsonElement typeName = get(wrapper, "type");
        final JsonElement data = get(wrapper, "data");
        final Type actualType = typeForName(typeName); 
        return context.deserialize(data, actualType);
    }

    private Type typeForName(final JsonElement typeElem) {
        try {
            return Class.forName(typeElem.getAsString());
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e);
        }
    }

    private JsonElement get(final JsonObject wrapper, String memberName) {
        final JsonElement elem = wrapper.get(memberName);
        if (elem == null) throw new JsonParseException("no '" + memberName + "' member found in what was expected to be an interface wrapper");
        return elem;
    }
}