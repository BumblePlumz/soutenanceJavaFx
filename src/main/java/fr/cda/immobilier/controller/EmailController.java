package fr.cda.immobilier.controller;

import fr.cda.immobilier.model.entity.Annonce;
import fr.cda.immobilier.utils.tools.EmailTools;
import fr.cda.immobilier.utils.tools.FilesTools;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller du formulaire d'envoie d'email
 */
public class EmailController {
    @FXML
    TextField emailFirstname, emailLastname, emailEmail;

    private List<Annonce> adsList = new ArrayList<>();
    private Boolean isSeloger;
    private Boolean isOuestFrance;

    /**
     * Envoie d'un email
     */
    public void sendEmail(){
        String firstname = emailFirstname.getText().trim();
        String lastname = emailLastname.getText().trim();
        String name = firstname+" "+lastname;
        name = name.trim();
        String email = emailEmail.getText().trim();
        Map<String, String> sendTo = new HashMap<>();
        sendTo.put("name", name);
        sendTo.put("email", email);
        FilesTools.saveTxtFile(adsList);
        EmailTools.getInstance().sendEmail(sendTo, FilesTools.fileTxtPath, isSeloger, isOuestFrance);

        SceneController.showValidAlert("Confirmation","Email envoyé");
        Stage currentStage = (Stage) emailEmail.getScene().getWindow();
        currentStage.close();
    }

    public void setAdsList(List<Annonce> adsList) {
        this.adsList = adsList;
    }

    public void setSeloger(Boolean seloger) {
        isSeloger = seloger;
    }

    public void setOuestFrance(Boolean ouestFrance) {
        isOuestFrance = ouestFrance;
    }
}
