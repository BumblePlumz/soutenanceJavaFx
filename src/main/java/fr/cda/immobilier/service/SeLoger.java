package fr.cda.immobilier.service;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.cda.immobilier.model.entity.Address;
import fr.cda.immobilier.utils.tools.LoggerTools;
import fr.cda.immobilier.model.entity.Annonce;
import fr.cda.immobilier.model.entity.Type;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Gestion du service scraping sur le site seloger
 */
public class SeLoger {
    private String seLogerApiBaseUrl = "https://autocomplete.svc.groupe-seloger.com/api/v2.0/auto/complete/fra/63/10/8/SeLoger?text=";
    private final String[] motifs = {"pièce", "chambre", "m²", "Terrain", "Étage"};
    private final Map<String, String> typeId = new HashMap<>();
    public SeLoger() {
        typeId.put("appartment", "1");
        typeId.put("house", "2");
        typeId.put("parking", "3");
        typeId.put("box", "3");
        typeId.put("field", "4");
    }

    /**
     * Fonction pour récupérer les annonces sur le site seloger
     * @param type type de bien
     * @param city ville
     * @param minPrice prix minimum
     * @param maxPrice prix maximum
     * @param minSize taille minimum
     * @param maxSize taille maximum
     * @return une liste d'annonce
     */
    public List<Annonce> scrapSeLoger(Type type, String city, String minPrice, String maxPrice, String minSize, String maxSize) {
        // Attributs
        List<Annonce> resultSet = new ArrayList<>();

        // Getting api code for adresse location
        try (WebClient webClient = new WebClient()) {
            Address address = seLogerAddressApi(city);

//            new String[]{"https://www.seloger.com/list.htm?projects=2,5&types=", "&natures=1,2,4&places=[{"inseeCodes":[","]}]&price=","&surface=","&mandatorycommodities=0&enterprise=0&qsVersion=1.0&m=search_refine-redirection-search_results"},
//            String baseUrl = siteWeb.getUrl()[0];
//            location = ((Seloger) siteWeb).apiCall(location);
//            urlScraping = baseUrl + ((Seloger) siteWeb).getTypeBien().get(typeBien) + siteWeb.getUrl()[1] + location + siteWeb.getUrl()[2] + prixMin + "/" + prixMax + siteWeb.getUrl()[3] + surfaceMin + "/" + surfaceMax + siteWeb.getUrl()[4];
//            System.out.println("URL=====>" + urlScraping);


            // Creating url String
            String url = "https://www.seloger.com/list.htm?projects=2,5&types=";
            url += typeId.get(type.getLabel());
            url += "&natures=1,2,4&places=[{\"inseeCodes\":[";
            url += address.getCodeSeloger();
            url += "]}]";
            if ((minPrice != null || maxPrice != null)) {
                url += "&price=";
                url += (minPrice != null && !minPrice.isEmpty()) ? minPrice : "NaN";
                url += "/";
                url += (maxPrice != null &&!maxPrice.isEmpty()) ? maxPrice : "NaN";
            }
            if (minSize != null || maxPrice != null) {
                url += "&surface=";
                url += (minSize != null && !minSize.isEmpty()) ? minSize : "NaN";
                url += "/";
                url += (maxSize != null && !maxSize.isEmpty()) ? maxSize : "NaN";
            }
            url += "&mandatorycommodities=0&enterprise=0&qsVersion=1.0&m=search_refine-redirection-search_results";

            System.out.println(url);

            // WebClient config
            webClient.getOptions().setUseInsecureSSL(true);
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setJavaScriptEnabled(false);
            HtmlPage htmlPage = webClient.getPage(url);

            // Getting every annonces on the first page
            List<HtmlElement> annonces = htmlPage.getByXPath("//div[@data-testid='sl.explore.card-container']");
            for (DomElement annonce : annonces) {
                Annonce result = new Annonce();
                // Getting the ad href
                HtmlElement hrefElement = annonce.getFirstByXPath(".//a[@data-testId='sl.explore.coveringLink']");
                String href = (hrefElement.getAttribute("href").charAt(0) == '/') ? "https://www.seloger.com" + hrefElement.getAttribute("href") : hrefElement.getAttribute("href");

                // Getting the ad title
                HtmlElement titleElement = annonce.getFirstByXPath(".//div[@data-test='sl.title']");
                String title = titleElement.getTextContent();

                // Getting the ad address [NOT HANDLED]
                //            HtmlElement addressElement = annonce.getFirstByXPath(".//div[@data-test='sl.address']");
                //            String addressScraped = addressElement.getTextContent();

                // Getting the ad priceElement
                HtmlElement priceElement = annonce.getFirstByXPath(".//div[@data-test='sl.price-label']");
                String priceString = priceElement.getTextContent();
                String price = priceString.replaceAll("[^0-9]", "");

                // Getting the ad infos
                HtmlElement infoElements = annonce.getFirstByXPath(".//ul[@data-test='sl.tagsLine']");
                List<HtmlElement> liElements = infoElements.getElementsByTagName("li");
                HashMap<String, String> infos = new HashMap<>();
                for (HtmlElement liElement : liElements) {
                    String textContent = liElement.getTextContent();
                    if (textContent.contains("pièce")) {
                        result.setRoom(textContent);
                    }
                    if (textContent.contains("chambre")) {
                        result.setBedroom(textContent);
                    }
                    if (textContent.contains("m²")) {
                        String[] parts = textContent.split(" ");
                        String sizeString = parts[0];
                        result.setSize(Integer.parseInt(sizeString));
                    }
                }

                // Getting the ad imgUrl
                HtmlElement divElement = annonce.getFirstByXPath(".//div[@data-testid='sl.explore.PhotosContainer']");
                HtmlElement imgUrlElement = divElement.getFirstByXPath(".//img");
                String imgUrl = (imgUrlElement != null) ? imgUrlElement.getAttribute("src") : "";

                // Getting the ad description
                HtmlElement descElement = annonce.getFirstByXPath(".//div[@data-testid='sl.explore.card-description']");
                String desc = (descElement != null) ? descElement.getTextContent() : "";

                // Going inside the ad
                HtmlPage insideAnnonce = (!href.contains("INTSL")) ? hrefElement.click() : null;
                if (insideAnnonce != null) {
                    HtmlElement descEl = insideAnnonce.getFirstByXPath(".//div[@class='ShowMoreText__UITextContainer-sc-1swit84-0 fDeZMv']/p");
                    String insideDesc = descEl.getTextContent();

                    HtmlElement insideImgUrlElement = insideAnnonce.getFirstByXPath(".//img");
                    String insideImgUrl = insideImgUrlElement.getAttribute("src");

                    result.setHref(href);
                    result.setTitle(title);
                    result.setDescription((desc.isEmpty()) ? insideDesc : desc);
                    result.setPrice(Integer.parseInt(price));
                    result.setImgUrl((imgUrl.isEmpty()) ? insideImgUrl : imgUrl);
                    result.setAddress(address);
                    result.setType(type);

                    resultSet.add(result);
                }
            }
        } catch (IOException e) {
            LoggerTools.logError("Classe SeLoger : erreur dans scrapSeloger()", e.getCause());
        }
        return resultSet;
    }

    /**
     * Appelle à l'api de seloger pour trouver les addresses
     * @param city ville
     * @return Address
     */
    public Address seLogerAddressApi(String city) throws IOException {
        Address result = new Address();

        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(seLogerApiBaseUrl + city);
        try {
            HttpResponse response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                // Get array
                JsonArray jsonArray = JsonParser.parseString(jsonResponse).getAsJsonArray();
                // Get first element
                JsonElement element = jsonArray.get(0);
                // Get jsonObjet from first element
                JsonObject jsonObject = element.getAsJsonObject();

                // Find code
                result.setCodeSeloger(jsonObject.get("Params").getAsJsonObject().get("ci").getAsString());

                // Find City
                result.setCity(jsonObject.get("Tag").getAsString().toLowerCase());

                // Find zips
                JsonArray zipsList = jsonObject.get("Meta").getAsJsonObject().get("Zips").getAsJsonArray();
                result.setPostcode(zipsList.get(0).getAsString());

                // Find department
                JsonArray zones = jsonObject.get("Meta").getAsJsonObject().get("HierarchicalZones").getAsJsonArray();
                for (JsonElement obj : zones) {
                    if (obj.getAsJsonObject().get("Type").getAsString().equals("Departement")){
                        result.setDepartment(obj.getAsJsonObject().get("Name").getAsString().toLowerCase());
                    }
                }
            } else {
                throw new IOException("Échec de la requête avec le code : " + response.getStatusLine().getStatusCode());
            }
        }catch (IOException e){
            LoggerTools.logError("Classe SeLoger : erreur dans seLogerApi()", e.getCause());
        } finally {
            httpGet.releaseConnection();
        }
        return result;
    }
}
