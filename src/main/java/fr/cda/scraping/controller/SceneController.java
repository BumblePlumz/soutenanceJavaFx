package fr.cda.scraping.controller;

import fr.cda.scraping.model.entity.User;
import fr.cda.scraping.utils.LoggerTools;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {
    private static SceneController instance;
    private Parent root;
    private Stage authStage;
    private Stage homeStage;
    private Scene scene;
    private User user = null; // Mise en cache de l'utilisateur après sa connexion

    public Parent getRoot() {
        return root;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }

    public Stage getHomeStage() {
        return homeStage;
    }

    public void setHomeStage(Stage homeStage) {
        this.homeStage = homeStage;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Stage getAuthStage() {
        return authStage;
    }

    public void setAuthStage(Stage authStage) {
        this.authStage = authStage;
    }

    // Constructeur privé pour empêcher l'instanciation
    private SceneController() {
    }

    // Design pattern Singleton (synchronized pour qu'il soit unique (atomique) sur tous les threads)
    public static synchronized SceneController getInstance() {
        if (instance == null) {
            instance = new SceneController();
        }
        return instance;
    }

    /**
     * Faire une redirection sans changer de fenêtre une fois Home ouvert
     *
     * @param event
     * @param viewPath
     */
    public void homeRedirect(ActionEvent event, String viewPath) {
        try {
            root = FXMLLoader.load(getClass().getResource(viewPath));
            homeStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            homeStage.setScene(scene);
            homeStage.show();
        } catch (NullPointerException | IOException e) {
            LoggerTools.logFatal("Class SceneController : Erreur dans redirect()", e.getCause());
        }
    }

    /**
     * Ouvrir la fenêtre principale de l'application
     */
    public void openHome() {
        try {
            closeAuthStage();
            root = FXMLLoader.load(getClass().getResource("/fr/cda/scraping/view/home.fxml"));
            scene = new Scene(root, 1280, 860);
            homeStage = new Stage();
            homeStage.setScene(scene);
            homeStage.setTitle("ScrapingExe");
            homeStage.show();
        } catch (NullPointerException | IOException e) {
            LoggerTools.logFatal("Class SceneController : Erreur dans openHome()", e.getCause());
        }
    }

    /**
     * Ouvrir une modale pour l'inscription
     */
    public void openRegisterForm() {
        try {
            Parent registerRoot = FXMLLoader.load(getClass().getResource("/fr/cda/scraping/view/registerForm.fxml"));
            Stage registerStage = new Stage();
            registerStage.setTitle("Formulaire d'inscription");
            registerStage.initModality(Modality.APPLICATION_MODAL);
            registerStage.setResizable(false);
            Scene scene = new Scene(registerRoot, 300, 400);
            registerStage.setScene(scene);
            registerStage.showAndWait();
        } catch (NullPointerException | IOException e) {
            LoggerTools.logFatal("Class SceneController : Erreur dans openRegisterForm()", e.getCause());
        }
    }

    /**
     * Ouvrir une modale pour récupérer son mot de passe
     */
    public void openResetPasswordForm() {
        try{
            Parent resetRoot = FXMLLoader.load(getClass().getResource("/fr/cda/scraping/view/resetPasswordForm.fxml"));
            Stage resetStage = new Stage();
            resetStage.setTitle("Formulaire de réinitialisation de mot de passe");
            resetStage.initModality(Modality.APPLICATION_MODAL);
            resetStage.setResizable(false);
            Scene scene = new Scene(resetRoot);
            resetStage.setScene(scene);
            resetStage.showAndWait();
        }catch(NullPointerException | IOException e) {
            LoggerTools.logFatal("Class SceneController : Erreur dans openResetPasswordForm()", e.getCause());
        }
    }
    /**
     * Affichage d'une alert personnalisée
     *
     * @param title [String] titre de l'alert
     * @param content [String] contenu de l'alert
     */
    public static void showAlert(String title, String content) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(content);
        a.showAndWait();
    }
    private void closeAuthStage() {
        if (authStage != null) {
            authStage.close();
        }
    }
}
