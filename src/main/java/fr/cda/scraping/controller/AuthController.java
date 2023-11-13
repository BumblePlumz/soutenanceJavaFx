package fr.cda.scraping.controller;

import fr.cda.scraping.model.entity.User;
import fr.cda.scraping.model.repository.UserRepository;
import fr.cda.scraping.utils.LoggerTools;
import fr.cda.scraping.utils.PasswordTools;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AuthController {
    private SceneController sc = SceneController.getInstance();
    @FXML
    Label passwordError, idError;
    @FXML
    TextField id;
    @FXML
    PasswordField password;

    /**
     * Vérification et exécution des authentifications
     */
    public void login(){;
        // Récupération des inputs
        String login = id.getText().trim();
        String pwd = password.getText().trim();

        // Traitement des inputs
        if (!isInputsValid(login, pwd)){
            User user = getUserByEmail(login);
            if (user != null && PasswordTools.checkPassword(pwd, user.getPassword())){
                SceneController.getInstance().setUser(user);
                sc.openHome();
            }else{
                SceneController.showErrorAlert("Identification non valide", "Un des champs renseignés n'est pas valide");
            }
        }
    }

    /**
     * Ouverture du formulaire d'inscription
     */
    public void openRegisterForm(){
        System.out.println("Début de l'enregistrement");
        SceneController.getInstance().openRegisterForm();
    }


    public void openResetPasswordForm(){
        System.out.println("Début de la régénération du mot de passe");
        SceneController.getInstance().openResetPasswordForm();
    }

    public void clearPwdLabel(){
        passwordError.setText("");
    }
    public void clearIdLabel(){
        idError.setText("");
    }

    /**
     * Vérifie si les champs 'inputs' ne sont pas vides et lance une alerte avec un affichage du champ à remplir.
     *
     * @param login
     * @param pwd
     * @return
     */
    private Boolean isInputsValid(String login, String pwd) {
        boolean isAlert = false;

        if (login.isEmpty()){
            idError.setStyle("-fx-text-fill: red;");
            idError.setOpacity(0.5);
            idError.setMouseTransparent(true);
            idError.setText("Veuillez remplir ce champ");
            isAlert = true;
        }
        if (pwd.isEmpty()){
            passwordError.setStyle("-fx-text-fill: red;");
            passwordError.setOpacity(0.5);
            passwordError.setMouseTransparent(true);
            passwordError.setText("Veuillez remplir ce champ");
            isAlert = true;
        }
        if (isAlert) {
            SceneController.showErrorAlert("Connexion non valide", "Un des champs renseignés n'est pas valide");
        }
        return isAlert;
    }

    /**
     * Récupère un utilisateur à partir de son adresse e-mail
     *
     * @param email L'adresse e-mail de l'utilisateur à rechercher
     * @return [User] Renvoie l'utilisateur ou null si il n'existe ou une erreur s'est produite
     */
    private User getUserByEmail(String email) {
        User user = null;
        try {
            UserRepository ur = new UserRepository();
            user = ur.findByEmail(email);
        } catch (IllegalArgumentException | NullPointerException e) {
            LoggerTools.logError("Class AuthController: Une erreur s'est produite dans la récupération de l'utilisateur", e);
            SceneController.showErrorAlert("Vérifier la connexion", "La connexion à la base de données a échoué");
        }
        return user;
    }



}
