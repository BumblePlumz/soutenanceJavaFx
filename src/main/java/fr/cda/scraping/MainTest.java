package fr.cda.scraping;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.cda.scraping.model.entity.Type;
import fr.cda.scraping.model.repository.TypeRepository;
import fr.cda.scraping.service.Scraping;
import fr.cda.scraping.service.SeLoger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainTest {
    public static void main(String[] args) throws IOException {

//         Il faut pour seLoger :
//        Params.ci = code
//        tag = city
//        Meta.HierarchicalZones.0.Name = department
//        Meta.Zips = cp

        TypeRepository tr = new TypeRepository();
        List<Type> list = tr.findAll();
        for (Type t : list) {
            System.out.println(t.toString());
        }




//        SeLoger s = new SeLoger();
//
//        // TEST seLogerAddressApi
//        Map<String, String> ville = s.seLogerAddressApi("Vannes");
//        System.out.println(ville.get("code"));
//        System.out.println(ville.get("city"));
//        System.out.println(ville.get("postcode"));
//        System.out.println(ville.get("department"));
//        System.out.println(ville.toString());

        // TEST scrapingSeLoger
//        s.scrapSeLoger();

    }
}
