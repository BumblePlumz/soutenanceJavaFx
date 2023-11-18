package fr.cda.scraping.controller;

import fr.cda.scraping.model.entity.Type;
import fr.cda.scraping.model.repository.TypeRepository;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    ChoiceBox<String> searchType;
    @FXML
    TextField searchAddress, searchMinPrice, searchMaxPrice, searchMinSize, searchMaxSize;
    @FXML
    Button searchBtnValidate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Choices Initialization
        System.out.println("Initialisation du HomeController");
        for (Type t : new TypeRepository().findAll()) {
            searchType.getItems().add(t.getLabel());
        }


        //

    }

    public void initiateSearch(){
        // Get inputs
        String type = searchType.getSelectionModel().getSelectedItem();
        String address = searchAddress.getText().trim();
        String minPrice = searchMinPrice.getText().trim();
        String maxPrice = searchMaxPrice.getText().trim();
        String minSize = searchMinSize.getText().trim();
        String maxSize = searchMaxSize.getText().trim();

        // Vérification empty()
        isInputsValid(address, minPrice, maxPrice, minSize, maxSize);

    }
    private Boolean isInputsValid(String address, String minPrice, String maxPrice, String minSize, String maxSize) {
        boolean isAlert = false;
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
    public void isSearchValid(){
//        System.out.println("Exécution du listener");
//        System.out.println(searchType.getValue());
//        System.out.println(searchAddress.getText());
        if (searchType.getValue() != null && searchAddress != null){
            System.out.println("Valeur ok !");
            searchBtnValidate.setDisable(searchType.getValue().isEmpty() && searchAddress.getText().isEmpty());
        }
    }
    private void setupErrorMsg(TextField tf) {
        tf.setStyle("-fx-text-fill: red;");
        tf.setOpacity(0.5);
        tf.setPromptText("Veuillez remplir ce champ");
    }
}
