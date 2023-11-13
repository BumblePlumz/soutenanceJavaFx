package fr.cda.scraping;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.cda.scraping.service.Scraping;
import org.w3c.dom.ls.LSOutput;

import java.io.IOException;

public class MainTest {
    public static void main(String[] args) {

//         Il faut pour seLoger :
//        Params.ci = code
//        tag = city
//        Meta.HierarchicalZones.0.Name = department
//        Meta.Zips = cp





        Scraping s = new Scraping();
        JsonObject jsonObject = null;
        JsonArray jsonArray = null;
        JsonElement jsonElement = null;
        try{
            jsonElement = s.seLogerAddressApi("Vannes");
        }catch (IOException e){
            System.out.println("erreur");
        }

        System.out.println(jsonElement.get);
//        // Iterate over each element in the array
//        for (JsonElement jsonEl : jsonArray) {
//            // Check if the element is a JsonObject
//            if (jsonEl.isJsonObject()) {
//                // Convert the element to a JsonObject
//                JsonObject jsonObj = jsonEl.getAsJsonObject();
//                System.out.println("Json Element : "+jsonEl);
//                // Check if the JsonObject has the "Display" field
//                if (jsonObj.has("Display")) {
//                    // Get the value of the "Display" field
//                    String displayValue = jsonObj.get("Display").getAsString();
//
//                    // Extract the city name from the "Display" value
//                    String cityName =displayValue;
//
//                    // Print the city name
//                    System.out.println("City: " + cityName);
//                }
//            }
//        }
    }
}
