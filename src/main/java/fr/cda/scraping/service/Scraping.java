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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scraping {

    public void scrapImmoNotaire(){

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


}
