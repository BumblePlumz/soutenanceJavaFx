package fr.cda.scraping.controller;

import fr.cda.scraping.model.entity.User;
import fr.cda.scraping.model.repository.UserRepository;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterFormController {
    @FXML
    TextField registerName, registerFirstname, registerEmail;
    @FXML
    PasswordField registerPassword;

    public void register(){
        String name = registerName.getText().trim();
        String firstname = registerFirstname.getText().trim();
        String email = registerEmail.getText().trim();
        String pwd = registerPassword.getText().trim();

        if (isInputsValid(name, firstname, email, pwd)){
            UserRepository ur = new UserRepository();
            ur.save(new User(name, firstname, email, pwd));
            SceneController.showValidAlert("Compte créé", "Votre compte utilisateur a été créé");
        }else{
            SceneController.showErrorAlert("Inscription non valide", "Il y a une erreur dans le formulaire");
        }

    }

    private Boolean isInputsValid(String firstname, String name, String email, String pwd) {
        boolean isAlert = false;

        if (firstname == null || firstname.isEmpty()){
            setupErrorMsg(registerFirstname);
            isAlert = true;
        }
        if (name == null || name.isEmpty()){
            setupErrorMsg(registerName);
            isAlert = true;
        }
        if (email == null || email.isEmpty()){
            setupErrorMsg(registerEmail);
            isAlert = true;
        }
        if (isAlert) {
            SceneController.showErrorAlert("Connexion non valide", "Un des champs renseignés n'est pas valide");
        }
        return isAlert;
    }

    private void setupErrorMsg(TextField tf) {
        tf.setStyle("-fx-text-fill: red;");
        tf.setOpacity(0.5);
        tf.setPromptText("Veuillez remplir ce champ");
    }
}
