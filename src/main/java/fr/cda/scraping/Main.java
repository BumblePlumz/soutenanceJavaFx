package fr.cda.scraping;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Font;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Font helloFont = Font.loadFont(getClass().getResourceAsStream("fonts/Hello.ttf"), 12);

        // Charger le fichier FXML
        Parent root = FXMLLoader.load(getClass().getResource("/fr/cda/scraping/view/auth.fxml"));

        // Créer la scène
        Scene scene = new Scene(root, 800, 800);

        // Définir la scène sur la fenêtre principale
        stage.setScene(scene);

        // Définir le titre de la fenêtre
        stage.setTitle("Votre Application JavaFX");

        // Afficher la fenêtre
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
