package fr.cda.immobilier.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HelpController {
    @FXML
    Button closeHelp;
    public void close(){
        Stage currentStage = (Stage) closeHelp.getScene().getWindow();
        currentStage.close();
    }
}
