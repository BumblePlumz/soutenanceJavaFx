package fr.cda.scraping.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class HomeController  {
    @FXML
    ChoiceBox<String> searchChoiceBox;
    @FXML
    TextField searchAddress, searchMinPrice, searchMaxPrice, searchMinSize, searchMaxSize;

    public void initiateSearch(){
        // Get inputs
        String type = searchChoiceBox.getSelectionModel().getSelectedItem();
        String address = searchAddress.getText().trim();
        String minPrice = searchMinPrice.getText().trim();
        String maxPrice = searchMaxPrice.getText().trim();
        String minSize = searchMinSize.getText().trim();
        String maxSize = searchMaxSize.getText().trim();

        // Vérification empty()
        isInputsValid(type, address, minPrice, maxPrice, minSize, maxSize);



    }
    private Boolean isInputsValid(String type, String address, String minPrice, String maxPrice, String minSize, String maxSize) {
        boolean isAlert = false;
        if (type == null || type.isEmpty()){
            // TODO gestion erreur
            isAlert = true;
        }
        if (address == null || address.isEmpty()){
            setupErrorMsg(searchAddress);
            isAlert = true;
        }
        if (minPrice == null || minPrice.isEmpty()){
            setupErrorMsg(searchMinPrice);
            isAlert = true;
        }
        if (maxPrice == null || maxPrice.isEmpty()){
            setupErrorMsg(searchMaxPrice);
        }
        if (minSize == null || minSize.isEmpty()){
            setupErrorMsg(searchMinSize);
        }
        if (maxSize == null || maxSize.isEmpty()){
            setupErrorMsg(searchMaxSize);
        }
        if (isAlert) {
            SceneController.showErrorAlert("Recherche non valide", "Un des champs n'est pas renseigné");
        }
        return isAlert;
    }

    private void setupErrorMsg(TextField tf) {
        tf.setStyle("-fx-text-fill: red;");
        tf.setOpacity(0.5);
        tf.setPromptText("Veuillez remplir ce champ");
    }
}
