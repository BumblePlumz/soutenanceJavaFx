package fr.cda.immobilier.utils.adapter;

import com.google.gson.*;
import fr.cda.immobilier.model.entity.Type;

/**
 * Gestion des types en gson
 */
public class TypeAdapter implements JsonSerializer<Type>, JsonDeserializer<Type> {
    /**
     * Décodage des classes Type en .json
     * @param json
     * @param typeOfT
     * @param context
     * @return
     * @throws JsonParseException
     */
    @Override
    public Type deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        long id = jsonObject.get("id").getAsLong();
        String label = jsonObject.get("label").getAsString();

        Type type = new Type();
        type.setId(id);
        type.setLabel(label);

        return type;
    }

    /**
     * Encodage des classes Type en .json
     * @param src
     * @param typeOfSrc
     * @param context
     * @return
     */
    @Override
    public JsonElement serialize(Type src, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", src.getId());
        jsonObject.addProperty("label", src.getLabel());

        return jsonObject;
    }
}
