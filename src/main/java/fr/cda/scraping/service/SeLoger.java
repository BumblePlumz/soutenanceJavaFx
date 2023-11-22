package fr.cda.scraping.service;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.Html;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.cda.scraping.model.entity.Annonce;
import fr.cda.scraping.model.entity.Type;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SeLoger {
    private String seLogerApiBaseUrl = "https://autocomplete.svc.groupe-seloger.com/api/v2.0/auto/complete/fra/63/10/8/SeLoger?text=";
    private final String[] motifs = {"pièce", "chambre", "m²", "Terrain", "Étage"};
    private final Map<String, String> typeId = new HashMap<>();
    public SeLoger() {
        typeId.put("appartment", "1");
        typeId.put("maison", "2");
        typeId.put("parking", "3");
        typeId.put("box", "3");
        typeId.put("field", "4");

    }

    public void scrapSeLoger(Type type, String city, String minPrice, String maxPrice, String minSize, String maxSize) throws IOException, InterruptedException {
        // Attributs
        Annonce result = new Annonce();

        // Getting api code for adresse location
        Map<String, String> address = seLogerAddressApi(city);

        // Creating url String
        String url = "https://www.seloger.com/list.htm?";
        url += "projects=1";
        url += "&types=" + typeId.get(type.getLabel());
        url += "&natures=1,2,4";
        url += "&places=[{\"inseeCodes\":[" + address.get("code") + "]}]";
        url += "&mandatorycommodities=0&enterprise=0&qsVersion=1.0&m=search_refine-redirection-search_results";
        if ((minPrice != null) || maxPrice != null){
            url += "&price=";
            url += (minPrice!= null ? minPrice : "NaN");
            url += "/";
            url += (maxPrice != null ? maxPrice : "NaN");
        }
        if ((minSize != null) || maxSize != null){
            url += "&surface=";
            url += (minSize != null ? minSize : "NaN");
            url += "/";
            url += (maxSize != null ? maxSize : "NaN");
        }
        String testUrl = "https://www.seloger.com/list.htm?projects=2,5&types=2,1&natures=1,2,4&places=[{%22inseeCodes%22:[560260]}]&price=NaN/250000&mandatorycommodities=0&enterprise=0&qsVersion=1.0&m=search_refine-redirection-search_results";

        // WebClient config
        WebClient webClient = new WebClient();
        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        HtmlPage htmlPage = webClient.getPage(url);

        // Getting every annonces on the first page
        List<HtmlElement> annonces = htmlPage.getByXPath("//div[@data-testid='sl.explore.card-container']");
        for (DomElement annonce : annonces) {
            // Getting the ad href
            HtmlElement hrefElement = annonce.getFirstByXPath(".//a[@data-testId='sl.explore.coveringLink']");
            String href = (hrefElement.getAttribute("href").charAt(0) == '/') ? "https://www.seloger.com"+hrefElement.getAttribute("href") : hrefElement.getAttribute("href");
//            System.out.println("----------------------------------------------------------------------------");
//            System.out.println(href);


            // Getting the ad title
            HtmlElement titleElement = annonce.getFirstByXPath(".//div[@data-test='sl.title']");
            String title = titleElement.getTextContent();
            System.out.println("----------------------------------------------------------------------------");
            System.out.println(title);

            // Getting the ad address
            HtmlElement addressElement = annonce.getFirstByXPath(".//div[@data-test='sl.address']");
            String addressScraped = addressElement.getTextContent();
            System.out.println("----------------------------------------------------------------------------");
            System.out.println(addressScraped);

            // Getting the ad priceElement
            HtmlElement priceElement = annonce.getFirstByXPath(".//div[@data-test='sl.price-label']");
            String price = priceElement.getTextContent();
            System.out.println("----------------------------------------------------------------------------");
            System.out.println(price);


            // Getting the ad infos
            HtmlElement infoElements = annonce.getFirstByXPath(".//ul[@data-test='sl.tagsLine']");
            List<HtmlElement> liElements = infoElements.getElementsByTagName("li");
            HashMap<String, String> infos = new HashMap<>();
            System.out.println("----------------------------------------------------------------------------");
            for (HtmlElement liElement : liElements) {
                String textContent = liElement.getTextContent();
                System.out.println(textContent);
            }


            HtmlElement divElement = annonce.getFirstByXPath(".//div[@data-testid='sl.explore.PhotosContainer']");
            HtmlElement imgUrlElement = divElement.getFirstByXPath(".//img");
            String imgUrl = (imgUrlElement != null) ? imgUrlElement.getAttribute("src") : "";
//            System.out.println("----------------------------------------------------------------------------");
//            System.out.println(imgUrl);

//            System.out.println("----------------------------------------------------------------------------");
//            System.out.println("Changement de page");
            // Going inside the ad
            HtmlPage insideAnnonce = (!href.contains("INTSL")) ? hrefElement.click() : null;
            if (insideAnnonce != null){
                System.out.println("=============================");
                System.out.println("Inside Annonce");
                System.out.println(insideAnnonce.getTitleText());

                HtmlElement descEl = insideAnnonce.getFirstByXPath(".//div[@class='ShowMoreText__UITextContainer-sc-1swit84-0 fDeZMv']/p");
                System.out.println("=============================");
                System.out.println(descEl);
                String descri = descEl.getTextContent();
                System.out.println(descri);

                HtmlElement insideImgUrlElement = insideAnnonce.getFirstByXPath(".//img");
                String insideImgUrl = insideImgUrlElement.getAttribute("src");
                System.out.println("=============================");
                System.out.println(insideImgUrl);

            }
            System.out.println("+++++++++++++++++++++ FIN ++++++++++++++++++++++++");

            result.setHref(href);
            result.setPrice(Integer.parseInt(price));

            // Description : ShowMoreText__UITextContainer-sc-1swit84-0 fDeZMv
        // Titre :
//        https://www.seloger.com/list.htm?projects=2,5&types=1&natures=1,2,4&places=[{%22inseeCodes%22:[560260]}]&mandatorycommodities=0&enterprise=0&qsVersion=1.0&m=search_refine-redirection-search_results
//        https://www.seloger.com/list.htm?tri=initial&enterprise=0&idtypebien=1&places=[{"inseeCodes":[560260]}]&mandatorycommodities=0&enterprise=0&qsVersion=1.0&m=search_refine-redirection-search_results
        }
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
                for (JsonElement obj : zones) {
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
