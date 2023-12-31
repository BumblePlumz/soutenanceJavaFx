package fr.cda.immobilier;

import fr.cda.immobilier.controller.SceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AppExe extends Application {
    /**
     * Exécutable du programme
     * @param authStage
     * @throws Exception
     */
    @Override
    public void start(Stage authStage) throws Exception {
        // Définition de la font
        Font helloFont = Font.loadFont(getClass().getResourceAsStream("fr/cda/css/fonts/Hello.ttf"), 12);

        // Charger le fichier FXML
        Parent root = FXMLLoader.load(getClass().getResource("/fr/cda/immobilier/view/home.fxml"));

        // Créer la scène
        Scene scene = new Scene(root, 800, 800);

        // Définir la scène sur la fenêtre principale
        authStage.setScene(scene);

        // Définir le titre de la fenêtre
        authStage.setTitle("Votre Application JavaFX");

        // Afficher la fenêtre
        authStage.show();

        // On sauvegarde l'adresse mémoire du stage pour pouvoir le fermer
        SceneController.getInstance().setAuthStage(authStage);
    }
    public static void main(String[] args) {
        launch(args);
    }

}
