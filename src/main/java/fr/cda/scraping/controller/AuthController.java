package fr.cda.scraping.controller;

import fr.cda.scraping.model.entity.User;
import fr.cda.scraping.model.repository.UserRepository;
import fr.cda.scraping.utils.JPATools;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class AuthController {
    @FXML
    TextField id, password;
    public void login(){
        System.out.println("Début de l'authentification");
        // Récupération des inputs
        String login = id.getText().trim();
        String pwd = password.getText().trim();

        System.out.println(login);
        System.out.println(pwd);

        // Traitement des inputs
        if (login.isEmpty() || pwd.isEmpty()){
            showAlert("Connexion invalide", "Un des champs renseignés n'est pas valide");
        }else{
            UserRepository ur = new UserRepository("scraping");
            User user = ur.findByEmail(login);
            if (user == null){

            }
        }


    }



    public void register(){
        System.out.println("Début de l'enregistrement");
    }
    public void resetPwd(){
        System.out.println("Début de la régénération du mot de passe");
    }

    private static void showAlert(String title, String content) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(content);
        a.showAndWait();
    }
}
