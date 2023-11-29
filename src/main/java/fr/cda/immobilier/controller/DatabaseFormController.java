package fr.cda.immobilier.controller;

import fr.cda.immobilier.utils.Config;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller du formulaire de base de données
 */
public class DatabaseFormController {
    @FXML
    TextField registerServer, registerPort, registerName, registerUser;
    @FXML
    PasswordField registerPwd;
    @FXML
    Button registerDbValidate;

    /**
     * Enregistrer une nouvelle base de données
     */
    public void register(){
        String server = registerServer.getText().trim();
        String port = registerPort.getText().trim();
        String dbName = registerName.getText().trim();
        String user = registerUser.getText().trim();
        String pwd = registerPwd.getText().trim();

        Map<String, String> data = new HashMap<>();
        if (!server.isEmpty()){
            data.put("database.server", server);
        }
        if (!port.isEmpty()){
            data.put("database.port", port);
        }
        if (!dbName.isEmpty()){
            data.put("database.dbname", dbName);
        }
        if (!user.isEmpty()){
            data.put("database.user", user);
        }
        if (!user.isEmpty()){
            data.put("database.password", pwd);
        }
        if (!data.isEmpty()){
            Config.updateProperty(data);
            SceneController.showValidAlert("Confirmation","Données enregistrées");
            Stage currentStage = (Stage) registerDbValidate.getScene().getWindow();
            currentStage.close();
        }else{
            SceneController.showErrorAlert("Erreur","Veuillez remplir au moins un champs");
        }
    }
}
