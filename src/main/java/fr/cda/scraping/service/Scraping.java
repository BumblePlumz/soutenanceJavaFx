package fr.cda.scraping.service;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.gson.*;
import fr.cda.scraping.model.entity.Annonce;
import fr.cda.scraping.model.entity.Type;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Scraping {
    public void scrapImmoNotaire(){

    }
    public void scrapSeLoger(Type type, String city, String minPrice, String maxPrice, String minSize, String maxSize){
        StringBuilder sb = new StringBuilder();
        sb.append("https://www.seloger.com/list.htm?tri=initial&enterprise=0&idtypebien=");
        sb.append(type);
        sb.append("&places=[{%22inseeCodes%22:[");
        sb.append(city);
        sb.append("]}]&price=");
        sb.append(minPrice);
        sb.append("/");
        sb.append(maxPrice);
        sb.append("&surface=");
        sb.append(minSize);
        sb.append("/");
        sb.append(maxSize);
        sb.append("&mandatorycommodities=0&enterprise=0&qsVersion=1.0&m=search_refine-redirection-search_results");

        WebClient webClient = new WebClient();
        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
//        HtmlPage htmlPage = webClient.getPage(url);
    }
    public void scrapOuestFrance(){

    }
    public void scrapLeBonCoin() throws IOException {
        String url = "https://www.leboncoin.fr/recherche?category=8&text=maison&locations=Vannes_56000__47.65834_-2.75985_5000_5000";

        WebClient webClient = new WebClient();

        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        HtmlPage htmlPage = webClient.getPage(url);

        //titre page
        System.out.println("Titre de la page " + htmlPage.getTitleText());

        List<Annonce> liste = new ArrayList<>();

//        //recup du charset
//        System.out.println(htmlPage.getCharset() + htmlPage.getContentType());
        String path = "//div/a[@class='sc-996f251d-0 leAMGT']";
        List<HtmlElement> list = htmlPage.getByXPath(path);
        for (HtmlElement e : list) {
            String imageUrl = "";
            String[] infos = new String[4];

            // URL image
            HtmlImage img = e.getFirstByXPath(".//img");
            imageUrl = img != null ? img.getSrcAttribute() : "";
            // 4 paragraphes contenant les infos
            infos[0] = extractText(e.getFirstByXPath(".//div/div[2]/div[1]/div/div/div[1]/div/p/span/span"));
            infos[1] = extractText(e.getFirstByXPath(".//div/div[2]/div[1]/div/div/div[1]/div/span/p"));
            infos[2] = extractText(e.getFirstByXPath(".//div/div[2]/div[1]/div/div/div[2]/p"));
            infos[3] = extractText(e.getFirstByXPath(".//div/div[2]/div[1]/p"));
            Annonce a = new Annonce();

            liste.add(a);
        }

        for (Annonce a :
                liste) {
            System.out.println(a);
        }
        String lien = "";
        for (HtmlElement el :
                list) {
            lien = el != null ? el.getAttribute("href") : "";
            System.out.println(lien);
        }
    }
    private static String extractText(HtmlElement element) {
        return element != null ? element.getTextContent() : "";
    }

    public List<> seLogerAddressApi(String city) throws IOException {
        String apiUrl = "https://autocomplete.svc.groupe-seloger.com/api/v2.0/auto/complete/fra/63/10/8/SeLoger?text=";
        String url = apiUrl + city;

        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);

        try {
            HttpResponse response = httpClient.execute(httpGet);

            // Vérifier si la réponse est réussie (code 200)
            if (response.getStatusLine().getStatusCode() == 200) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                JsonArray jsonArray = JsonParser.parseString(jsonResponse).getAsJsonArray();
                return ;
            } else {
                throw new IOException("Échec de la requête avec le code : " + response.getStatusLine().getStatusCode());
            }
        } finally {
            // Assurez-vous de libérer les ressources associées à la requête HTTP
            httpGet.releaseConnection();
        }
    }
}
