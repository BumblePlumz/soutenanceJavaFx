package fr.cda.immobilier.utils.adapter;

import fr.cda.immobilier.model.entity.Address;
import com.google.gson.*;

/**
 * Gestion des adresses en gson
 */
public class AddressAdapter implements JsonSerializer<Address>, JsonDeserializer<Address> {
    /**
     * Décodage d'une classe address en .json
     * @param json
     * @param typeOfT
     * @param context
     * @return
     * @throws JsonParseException
     */
    @Override
    public Address deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        long id = jsonObject.get("id").getAsLong();
        String codeSeloger = (jsonObject.get("codeSeloger") != null) ? jsonObject.get("codeSeloger").getAsString() : "";
        String codeOuestFrance = (jsonObject.get("codeOuestFrance") != null) ? jsonObject.get("codeOuestFrance").getAsString() : "";
        String city = jsonObject.get("city").getAsString();
        String department = jsonObject.get("department").getAsString();
        String postcode = jsonObject.get("postcode").getAsString();

        Address address = new Address();
        address.setId(id);
        address.setCodeSeloger(codeSeloger);
        address.setCodeOuestFrance(codeOuestFrance);
        address.setCity(city);
        address.setDepartment(department);
        address.setPostcode(postcode);

        return address;
    }

    /**
     * Encodage d'une classe address en fichier .json
     * @param src
     * @param typeOfSrc
     * @param context
     * @return
     */
    @Override
    public JsonElement serialize(Address src, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", src.getId());
        jsonObject.addProperty("codeSeloger", src.getCodeSeloger());
        jsonObject.addProperty("codeOuestFrance", src.getCodeOuestFrance());
        jsonObject.addProperty("city", src.getCity());
        jsonObject.addProperty("department", src.getDepartment());
        jsonObject.addProperty("postcode", src.getPostcode());

        return jsonObject;
    }
}
