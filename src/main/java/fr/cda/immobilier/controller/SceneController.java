package fr.cda.immobilier.controller;

import fr.cda.immobilier.model.entity.Annonce;
import fr.cda.immobilier.model.entity.User;
import fr.cda.immobilier.utils.tools.LoggerTools;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/**
 * Controller qui s'occupe des routes et des stages
 */
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
            root = FXMLLoader.load(getClass().getResource("/fr/cda/immobilier/view/home.fxml"));
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
     * Ouvrir une modale pour le formulaire de base de données
     */
    public void openBDForm(){
        try{
            Parent registerRoot = FXMLLoader.load(getClass().getResource("/fr/cda/immobilier/view/databaseForm.fxml"));
            Stage registerStage = new Stage();
            registerStage.setTitle("Formulaire de base de données");
            registerStage.initModality(Modality.APPLICATION_MODAL);
            registerStage.setResizable(false);
            Scene scene = new Scene(registerRoot);
            registerStage.setScene(scene);
            registerStage.showAndWait();
        } catch (NullPointerException | IOException e) {
            LoggerTools.logFatal("Class SceneController : Erreur dans openRegisterForm()", e.getCause());
        }
    }

    /**
     * Ouvrir le formulaire d'envoie d'email
     * @param adsList liste d'annonces
     * @param isSeloger le checkbox seloger
     * @param isOuestFrance le checkbox ouestfrance
     */
    public void openEmailForm(List<Annonce> adsList, Boolean isSeloger, Boolean isOuestFrance) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/cda/immobilier/view/email.fxml"));
            Parent emailRoot = loader.load();

            // Obtenez le contrôleur associé au fichier FXML
            EmailController controller = loader.getController();

            // Appelez la méthode pour transmettre les annonces
            controller.setAdsList(adsList);
            controller.setSeloger(isSeloger);
            controller.setOuestFrance(isOuestFrance);

            Stage emailStage = new Stage();
            emailStage.setTitle("Formulaire d'email");
            emailStage.initModality(Modality.APPLICATION_MODAL);
            emailStage.setResizable(false);
            Scene scene = new Scene(emailRoot);
            emailStage.setScene(scene);
            emailStage.showAndWait();
        } catch (IOException e) {
            LoggerTools.logFatal("Class SceneController : Erreur dans openEmailForm()", e.getCause());
        }
    }

    /**
     * Ouvrir une modale pour l'inscription
     */
    public void openRegisterForm() {
        try {
            Parent registerRoot = FXMLLoader.load(getClass().getResource("/fr/cda/immobilier/view/registerForm.fxml"));
            Stage registerStage = new Stage();
            registerStage.setTitle("Mode d'emploi");
            registerStage.initModality(Modality.APPLICATION_MODAL);
            registerStage.setResizable(false);
            Scene scene = new Scene(registerRoot);
            registerStage.setScene(scene);
            registerStage.showAndWait();
        } catch (NullPointerException | IOException e) {
            LoggerTools.logFatal("Class SceneController : Erreur dans openRegisterForm()", e.getCause());
        }
    }
    /**
     * Ouvrir le mode d'emploi
     */
    public void openHelpWindow(){
        try{
            Parent resetRoot = FXMLLoader.load(getClass().getResource("/fr/cda/immobilier/view/help.fxml"));
            Stage resetStage = new Stage();
            resetStage.setTitle("Formulaire de réinitialisation de mot de passe");
            resetStage.initModality(Modality.APPLICATION_MODAL);
            resetStage.setResizable(false);
            Scene scene = new Scene(resetRoot);
            resetStage.setScene(scene);
            resetStage.showAndWait();
        }catch(NullPointerException | IOException e) {
            LoggerTools.logFatal("Class SceneController : Erreur dans openHelpWindow()", e.getCause());
        }
    }
    /**
     * Ouvrir une modale pour récupérer son mot de passe
     */
    public void openResetPasswordForm() {
        try{
            Parent resetRoot = FXMLLoader.load(getClass().getResource("/fr/cda/immobilier/view/resetPasswordForm.fxml"));
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
    public static void showErrorAlert(String title, String content) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(content);
        a.showAndWait();
    }
    public static void showValidAlert(String title, String content){
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
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
