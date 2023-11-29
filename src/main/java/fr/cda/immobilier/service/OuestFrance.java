package fr.cda.immobilier.service;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomText;
import com.gargoylesoftware.htmlunit.html.Html;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.cda.immobilier.model.entity.Address;
import fr.cda.immobilier.model.entity.Type;
import fr.cda.immobilier.utils.tools.LoggerTools;
import fr.cda.immobilier.model.entity.Annonce;
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
 * Gestion du scraping sur le site ouest france
 */
public class OuestFrance {
    private final Map<String, String> typeId = new HashMap<>();
    public OuestFrance() {
        typeId.put("appartment", "appartement");
        typeId.put("house", "maison");
        typeId.put("field", "garage-box-et-parking");
        typeId.put("other", "terrain");
    }

    /**
     * Fonction pour récupérer les annonces sur le site ouestFrance
     * @param type type de bien
     * @param city ville
     * @param minPrice prix minimum
     * @param maxPrice prix maximum
     * @param minSize taille minimum
     * @param maxSize taille maximum
     * @return une liste d'annonce
     */
    public List<Annonce> scrapOuestFrance(Type type, String city, String minPrice, String maxPrice, String minSize, String maxSize) {
        Address address = ouestFranceApi(city);
        List<Annonce> resultSet = new ArrayList<>();

        String url = "https://www.ouestfrance-immo.com/";
        url += "acheter/"; // Change this to rent (louer) if you want the fonctionality
        url += typeId.get(type.getLabel())+"/";
        url += address.getCity()+"-"+address.getCodeOuestFrance()+"-"+address.getPostcode()+"/";
        url += "?tri=geo-desc";
        if (minPrice != null || maxPrice != null){
            url += "&prix=";
            url += (minPrice != null && !minPrice.isEmpty()) ? minPrice : "0";
            url += "_";
            url += (maxPrice != null &&!maxPrice.isEmpty()) ? maxPrice : "0";
        }
        if (minSize != null || maxPrice != null){
            url += "&surface=";
            url += (minSize != null && !minSize.isEmpty()) ? minSize : "0";
            url += "_";
            url += (maxSize != null && !maxSize.isEmpty()) ? maxSize : "0";
        }

        WebClient webClient = new WebClient();
        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        try {
            HtmlPage htmlPage = webClient.getPage(url);

            List<HtmlElement> annonces = htmlPage.getByXPath("//div/a[@class='annLink  ']");
            for (HtmlElement annonce : annonces) {
                Annonce ad = new Annonce();
                ad.setType(type);
                ad.setAddress(address);

                String href = "https://www.ouestfrance-immo.com"+annonce.getAttribute("href");
                ad.setHref(href);

                HtmlElement annonceElement = annonce.getFirstByXPath("./div");
                HtmlElement priceElement = annonceElement.getFirstByXPath(".//span[@class='annPrix']");
                String price = priceElement.getTextContent().trim().replaceAll("[^0-9]", "");
                ad.setPrice(Integer.parseInt(price));

                HtmlElement titleElement = annonceElement.getFirstByXPath(".//span[@class='annTitre']");
                String title = titleElement.getTextContent().trim();
                ad.setTitle(title);

                List<HtmlElement> infosElement = annonceElement.getByXPath(".//span[@class='annCriteres']/div");
                String size = "";
                String room = "";
                String bedroom = "";
                for (HtmlElement element : infosElement) {
                    HtmlElement unit = element.getFirstByXPath(".//span[@class='unit']");
                    if (unit.getTextContent().contains("m²")){
                        size = element.getTextContent().trim().replaceAll("\\D+", "");
                    }
                    if (unit.getTextContent().contains("chb")){
                        room = element.getTextContent().trim().replaceAll("\\D+", "");
                    }
                    if (unit.getTextContent().contains("sdb")){
                        bedroom = element.getTextContent().trim().replaceAll("\\D+", "");
                    }
                }
                ad.setSize(Integer.parseInt(size));
                ad.setRoom(room);
                ad.setBedroom(bedroom);

                HtmlPage insideAnnonce = annonce.click();
                String desc = "";
                if (insideAnnonce != null){
                    HtmlElement descElement = insideAnnonce.getFirstByXPath(".//div[@class='txtAnn']");
                    if (descElement != null) {
                        // Récupération de tous les nœuds texte à l'intérieur de la div
                        List<DomText> textNodes = descElement.getByXPath(".//text()");
                        StringBuilder descBuilder = new StringBuilder();
                        for (DomText textNode : textNodes) {
                            // Concaténation des textes des nœuds texte (en évitant les <p>)
                            if (!"p".equalsIgnoreCase(textNode.getParentNode().getNodeName())) {
                                descBuilder.append(textNode.getTextContent().trim()).append(" ");
                            }
                        }
                        desc = descBuilder.toString().trim();
                    }

                    HtmlElement imgElement = insideAnnonce.getFirstByXPath("//div[@id='sliderLoader']/img");
                    String imgUrl = imgElement.getAttribute("src");
                    ad.setImgUrl(imgUrl);
                }
                ad.setDescription(desc);
                resultSet.add(ad);
            }
        } catch (IOException | NullPointerException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }

    /**
     * Appelle à l'api de ouest france immo pour trouver les addresses
     * @param city ville
     * @return Address
     */
    public Address ouestFranceApi(String city){
        Address result = new Address();
        String url ="https://www-api.ouestfrance-immo.com/api/lieux/?addQV=1&limit=50&order=desc&search="+city+"&sort=nb_ann&type=arrondissement%2Cquartier%2Cville";

        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                System.out.println(jsonResponse);
                // Get jsonObject
                JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

                // Get jsonArray
                JsonArray jsonArray = jsonObject.get("data").getAsJsonArray();
                // Get jsonObject from first element
                JsonObject json = jsonArray.get(0).getAsJsonObject();

                // Find code
                result.setCodeOuestFrance(json.get("parent").getAsJsonObject().get("code").getAsString());

                // Find City
                result.setCity(json.get("libelle").getAsString().toLowerCase());

                // Find zip
                result.setPostcode(json.get("cp").getAsString());

                // Find department
                result.setDepartment(json.get("parent").getAsJsonObject().get("libelle").getAsString().toLowerCase());
            } else {
                throw new IOException("Échec de la requête avec le code : " + response.getStatusLine().getStatusCode());
            }
        } catch (IOException e) {
            LoggerTools.logError("Classe OuestFrance : erreur dans ouestFranceApi()", e.getCause());
        } finally {
            httpGet.releaseConnection();
        }
        return result;
    }
}
