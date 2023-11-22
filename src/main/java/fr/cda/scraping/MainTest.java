package fr.cda.scraping;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.cda.scraping.model.entity.Type;
import fr.cda.scraping.model.repository.TypeRepository;
import fr.cda.scraping.service.Scraping;
import fr.cda.scraping.service.SeLoger;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainTest {
    public static void main(String[] args) throws IOException, InterruptedException {

//         Il faut pour seLoger :
//        Params.ci = code
//        tag = city
//        Meta.HierarchicalZones.0.Name = department
//        Meta.Zips = cp

//        TypeRepository tr = new TypeRepository();
//        List<Type> list = tr.findAll();
//        for (Type t : list) {
//            System.out.println(t.toString());
//        }
//
//        WebClient webClient = new WebClient();
//        webClient.getOptions().setUseInsecureSSL(true);
//        webClient.getOptions().setCssEnabled(false);
//        webClient.getOptions().setJavaScriptEnabled(false);
//
//        String inseeCode1 = "560260";
//        String projects = "2,5";
//        String types = "2,1";
//        String natures = "1,2,4";
//
//
//
//        System.out.println(url);
//
//        // Accès à la page avec WebClient
//        HtmlPage page = webClient.getPage(url);
//        System.out.println(page);



        SeLoger s = new SeLoger();
//
//        TEST seLogerAddressApi
//        Map<String, String> ville = s.seLogerAddressApi("Vannes");
//        System.out.println(ville.get("code"));
//        System.out.println(ville.get("city"));
//        System.out.println(ville.get("postcode"));
//        System.out.println(ville.get("department"));
//        System.out.println(ville.toString());

//      TEST scrapingSeLoger
        Type t = new Type();
        t.setLabel("appartment");
        s.scrapSeLoger(t, "Vannes", null, null, null, null);


    }
}
