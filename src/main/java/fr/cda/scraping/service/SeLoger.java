package fr.cda.scraping.service;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.cda.scraping.model.entity.Type;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SeLoger {
    private String seLogerApiBaseUrl = "https://autocomplete.svc.groupe-seloger.com/api/v2.0/auto/complete/fra/63/10/8/SeLoger?text=";

    public SeLoger() {
    }

    public void scrapSeLoger(Type type, String city, String minPrice, String maxPrice, String minSize, String maxSize) throws IOException {
        Map<String, String> address = seLogerAddressApi(city);

        StringBuilder sb = new StringBuilder();
        sb.append("https://www.seloger.com/list.htm?tri=initial&enterprise=0&idtypebien=");
        sb.append(type.getLabel());
        sb.append("&places=[{%22inseeCodes%22:[");
        sb.append(address.get("code"));
        if ((minSize != null) && !minSize.isEmpty()){

        }
        https://www.seloger.com/list.htm?projects=2,5&types=2,1&natures=1,2,4&places=[{%22divisions%22:[2238]}]&price=100000/300000&mandatorycommodities=0&enterprise=0&qsVersion=1.0&m=search_refine-redirection-search_results
        sb.append("]}]&price=");
        sb.append(minPrice);
        sb.append("/");
        sb.append(maxPrice);
        sb.append("&surface=");
        sb.append(minSize);
        sb.append("/");
        sb.append(maxSize);
        sb.append("&mandatorycommodities=0&enterprise=0&qsVersion=1.0&m=search_refine-redirection-search_results");
        String url = sb.toString();
        WebClient webClient = new WebClient();
        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        HtmlPage htmlPage = webClient.getPage(url);

//        List<HtmlElement> annonces = htmlPage.getBy
    }

    public Map<String, String> seLogerAddressApi(String city) throws IOException {
        Map<String, String> resultSet = new HashMap<>();

        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(seLogerApiBaseUrl + city);

        try {
            HttpResponse response = httpClient.execute(httpGet);

            // Vérifier si la réponse est réussie (code 200)
            if (response.getStatusLine().getStatusCode() == 200) {
                String jsonResponse = EntityUtils.toString(response.getEntity());

                // Get array
                JsonArray jsonArray = JsonParser.parseString(jsonResponse).getAsJsonArray();
                // Get first element
                JsonElement element = jsonArray.get(0);
                // Get jsonObjet from first element
                JsonObject jsonObject = element.getAsJsonObject();
                // Find code
                resultSet.put("code", jsonObject.get("Params").getAsJsonObject().get("ci").getAsString());
                // Find City
                resultSet.put("city", jsonObject.get("Tag").getAsString());
                // Find zips
                String zips = "";
                JsonArray zipsList = jsonObject.get("Meta").getAsJsonObject().get("Zips").getAsJsonArray();
                for (JsonElement zip : zipsList) {
                    zips += zip.getAsString();
                }
                resultSet.put("postcode", zips);
                // Find department
                JsonArray zones = jsonObject.get("Meta").getAsJsonObject().get("HierarchicalZones").getAsJsonArray();
                System.out.println(zones);
                for (JsonElement obj : zones) {
                    System.out.println(obj);
                    if (obj.getAsJsonObject().get("Type").getAsString().equals("Departement")){
                        resultSet.put("department", obj.getAsJsonObject().get("Name").getAsString());
                    }
                }
            } else {
                throw new IOException("Échec de la requête avec le code : " + response.getStatusLine().getStatusCode());
            }
        } finally {
            // Assurez-vous de libérer les ressources associées à la requête HTTP
            httpGet.releaseConnection();
        }
        return resultSet;
    }
}
