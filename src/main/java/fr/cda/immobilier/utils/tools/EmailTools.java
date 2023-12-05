package fr.cda.immobilier.utils.tools;

import sendinblue.ApiClient;
import sendinblue.Configuration;
import sendinblue.auth.ApiKeyAuth;
import sibApi.TransactionalEmailsApi;
import sibModel.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Gestion des emails
 */
public class EmailTools {
    private String key = "";
    private static EmailTools instance = null;

    private EmailTools() {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        // Configure API key authorization: api-key
        ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
        apiKey.setApiKey(key);
    }
    public static EmailTools getInstance(){
        if (instance == null){
            instance = new EmailTools();
        }
        return instance;
    }

    /**
     * Envoie d'un email
     * @param sendTo destinataire (email, name)
     * @param filePath fichier à envoyer
     * @param isSeloger ajouter seloger au texte
     * @param isOuestFrance ajouter ouest france au texte
     */
    public void sendEmail(Map<String, String> sendTo, String filePath, Boolean isSeloger, Boolean isOuestFrance) {

        try{
            // Instance of API
            TransactionalEmailsApi api = new TransactionalEmailsApi();

            // Sender
            SendSmtpEmailSender sender = new SendSmtpEmailSender();
            sender.setEmail("contact@bumblefamily.fr");
            sender.setName("Scraping Immobilier");

            // Send to ?
            SendSmtpEmailTo to = new SendSmtpEmailTo();
            to.setEmail(sendTo.get("email"));
            to.setName(sendTo.get("name"));
            List<SendSmtpEmailTo> toList = new ArrayList<>();
            toList.add(to);

            // Ads listing as joint document
            SendSmtpEmailAttachment attachment = new SendSmtpEmailAttachment();
            attachment.setName("annonce.txt");
            byte[] encode = Files.readAllBytes(Paths.get(filePath));
            attachment.setContent(encode);
            List<SendSmtpEmailAttachment> attachmentList = new ArrayList<SendSmtpEmailAttachment>();
            attachmentList.add(attachment);

            Properties headers = new Properties();
            headers.setProperty("Les dernières annonces !", "unique-id-1234");

            Properties params = new Properties();
            params.setProperty("seLoger", "https://www.seloger.com/");
            params.setProperty("ouestFrance", "https://www.ouestfrance-immo.com/");
            params.setProperty("subject", "Scraping immobilier");

            // Email object
            SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();
            sendSmtpEmail.setSender(sender);
            sendSmtpEmail.setTo(toList);
            String content = "<html><body><h1>Voici la liste des dernières annonces immobilières</h1>";
            if (isSeloger) {
                content += "<p>Annonces SeLoger : " + params.get("seLoger") + "</p>";
            }
            if (isOuestFrance) {
                content += "<p>Annonces Ouest France : " + params.get("ouestFrance") + "</p>";
            }
            content += "</body></html>";

            sendSmtpEmail.setHtmlContent(content);
            sendSmtpEmail.setSubject("My {{params.subject}}");
            sendSmtpEmail.setAttachment(attachmentList);
            sendSmtpEmail.setHeaders(headers);
            sendSmtpEmail.setParams(params);

            // Send the email
            CreateSmtpEmail response = api.sendTransacEmail(sendSmtpEmail);
        }catch (Exception e) {
            LoggerTools.logError("Class EmailsTools : erreur dans sendEmail()", e.getCause());
        }
    }

    public void setKey(String key) {
        this.key = key;
    }
}
