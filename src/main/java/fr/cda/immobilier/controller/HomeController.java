package fr.cda.immobilier.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import fr.cda.immobilier.model.entity.Address;
import fr.cda.immobilier.model.entity.Annonce;
import fr.cda.immobilier.model.entity.Type;
import fr.cda.immobilier.model.repository.AnnonceRepository;
import fr.cda.immobilier.model.repository.TypeRepository;
import fr.cda.immobilier.service.OuestFrance;
import fr.cda.immobilier.service.SeLoger;
import fr.cda.immobilier.utils.adapter.AddressAdapter;
import fr.cda.immobilier.utils.adapter.LocalDateTimeAdapter;
import fr.cda.immobilier.utils.adapter.TypeAdapter;
import fr.cda.immobilier.utils.listcell.AnnonceListCell;
import fr.cda.immobilier.utils.tools.LoggerTools;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controleur de la page principale de l'application
 */
public class HomeController implements Initializable {
    @FXML
    ChoiceBox<String> searchType;
    @FXML
    TextField searchAddress, searchMinPrice, searchMaxPrice, searchMinSize, searchMaxSize;
    @FXML
    Button searchBtnValidate, sourceBtnValidate;
    @FXML
    CheckBox isSeloger, isOuestFrance;
    @FXML
    Accordion stepsAccordion;
    @FXML
    TitledPane stepOne, stepTwo;
    @FXML
    ListView<Annonce> annonceListView;
    @FXML
    ProgressBar progressBar;
    private final ObservableList<Annonce> annonceObservableList = FXCollections.observableArrayList();
    private boolean isStepOne = false;

    /**
     * Ressources chargées automatiquement
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Choices Initialization
        System.out.println("Initialisation du HomeController");
        for (Type t : new TypeRepository().findAll()) {
            searchType.getItems().add(t.getLabel());
        }
        // Observable list initialization
        annonceListView.setCellFactory(param -> new AnnonceListCell());
        annonceListView.setItems(annonceObservableList);

        // Listening StepOne
        searchType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> isSearchValid());
    }

//    /**
//     * Lancer une recherche d'annonce
//     */
//    public void initiateSearch(){
//        // Get inputs
//        String label = searchType.getSelectionModel().getSelectedItem();
//        Type type = new TypeRepository().findByLabel(label);
//        String address = searchAddress.getText().trim();
//        String minPrice = searchMinPrice.getText().trim();
//        String maxPrice = searchMaxPrice.getText().trim();
//        String minSize = searchMinSize.getText().trim();
//        String maxSize = searchMaxSize.getText().trim();
//
//        List<Annonce> result = new ArrayList<>();
//        if (isSeloger.isSelected()){
//            result.addAll(new SeLoger().scrapSeLoger(type, address, minPrice, maxPrice, minSize, maxSize));
//        }
//        if (isOuestFrance.isSelected()){
//            result.addAll(new OuestFrance().scrapOuestFrance(type, address, minPrice, maxPrice, minSize, maxSize));
//        }
//        for (Annonce a : result) {
//            if (!annonceObservableList.contains(a)){
//                annonceObservableList.add(a);
//            }
//        }
//        annonceListView.setCellFactory(param -> new AnnonceListCell());
//        annonceListView.setItems(annonceObservableList);
//    }
    /**
     * Lancer une recherche d'annonce
     */
    public void initiateSearch(){
        /**
         * Nouvelle tâche
         */
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                String label = searchType.getSelectionModel().getSelectedItem();
                Type type = new TypeRepository().findByLabel(label);
                String address = searchAddress.getText().trim();
                String minPrice = searchMinPrice.getText().trim();
                String maxPrice = searchMaxPrice.getText().trim();
                String minSize = searchMinSize.getText().trim();
                String maxSize = searchMaxSize.getText().trim();

                List<Annonce> result = new ArrayList<>();
                if (isSeloger.isSelected()){
                    System.out.println("seloger début");
                    result.addAll(new SeLoger().scrapSeLoger(type, address, minPrice, maxPrice, minSize, maxSize));
                    System.out.println("seLoger fini");
                }
                if (isOuestFrance.isSelected()){
                    System.out.println("ouestfrance début");
                    result.addAll(new OuestFrance().scrapOuestFrance(type, address, minPrice, maxPrice, minSize, maxSize));
                    System.out.println("ouestfrance fini");
                }

                for (int i = 0; i < result.size(); i++) {
                    if (!annonceObservableList.contains(result.get(i))){
                        annonceObservableList.add(result.get(i));
                    }

                    // Mise à jour de la barre de progression (calculer la progression en fonction des étapes effectuées)
                    updateProgress(i + 1, result.size());
                }

                return null;
            }
        };
        // Handling progress bar
        progressBar.progressProperty().bind(task.progressProperty());
        Thread searchThread = new Thread(task);
        searchThread.setDaemon(true);
        searchThread.start();
        // Setting up ads
        annonceListView.setCellFactory(param -> new AnnonceListCell());
        annonceListView.setItems(annonceObservableList);
    }

    /**
     * Charger un fichier .json
     */
    public void loadFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir un fichier *.json");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers json", "*.json"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null){

            try (FileReader fileReader = new FileReader(file);
                 BufferedReader bufferedReader = new BufferedReader(fileReader)) {

                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                        .registerTypeAdapter(Type.class, new TypeAdapter())
                        .registerTypeAdapter(Address.class, new AddressAdapter())
                        .excludeFieldsWithoutExposeAnnotation()
                        .create();
                List<Annonce> annonces = gson.fromJson(bufferedReader, new TypeToken<List<Annonce>>(){}.getType());
                for (Annonce a : annonces) {
                    if (!annonceObservableList.contains(a)){
                        annonceObservableList.add(a);
                    }
                }
            } catch (Exception e) {
                LoggerTools.logError("Class HomeController : erreur dans loadFile", e.getCause());
            }
        }
    }

    /**
     * Sauvegarder un fichier .json
     */
    public void saveFile(){
        // Setting up filechooser window
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer les données");
        fileChooser.setInitialFileName("annonces.json");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Fichiers json (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(SceneController.getInstance().getHomeStage());
        if (file != null){
            try (FileWriter writer = new FileWriter(file)) {
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                        .registerTypeAdapter(Type.class, new TypeAdapter())
                        .registerTypeAdapter(Address.class, new AddressAdapter())
                        .excludeFieldsWithoutExposeAnnotation()
                        .setPrettyPrinting()
                        .create();

                gson.toJson(annonceObservableList, writer);
            } catch (IOException e) {
                LoggerTools.logError("Class HomeController : erreur dans saveFile", e.getCause());
            }
        }
    }

    /**
     * Ouvrir le formulaire de base de données
     */
    public void openDBForm(){
        System.out.println("Début openForm");
        SceneController.getInstance().openBDForm();
    }

    /**
     * Lancer une sauvegarde dans la base de données
     */
    public void saveDataBase(){
        AnnonceRepository ar = new AnnonceRepository();
        ar.updateCollection(annonceObservableList);
        LoggerTools.logInfo("Class HomeController : Sauvegarde dans la base de données effectuée");
    }

    /**
     * Ouverture du formulaire d'envoie d'email
     */
    public void openEmailForm(){
        System.out.println("Début openEmailForm");
        SceneController.getInstance().openEmailForm(annonceObservableList, isSeloger.isSelected(), isOuestFrance.isSelected());
    }

    /**
     * Gestion interne des étapes du formulaire de recherche
     */
    public void sendToStepTwo(){
        stepsAccordion.setExpandedPane(stepTwo);
    }

    /**
     * Ouvrir la fenêtre d'aide
     */
    public void openHelp(){
        SceneController.getInstance().openHelpWindow();
    }

    /**
     * Effacer les champs
     */
    public void removeInputs(){
        searchAddress.clear();
        searchMinPrice.clear();
        searchMaxPrice.clear();
        searchMinSize.clear();
        searchMaxSize.clear();
    }
    /**
     * Gestion du bouton pour lancer une recherche sur les paramètres de recherches
     */
    public void isSearchValid(){
        if (searchType.getSelectionModel().getSelectedItem() != null && !searchAddress.getText().isEmpty()){
            searchBtnValidate.setDisable(false);
            isStepOne = true;
            isSourcesValid();
        }else{
            searchBtnValidate.setDisable(true);
            isStepOne = false;
            isSourcesValid();
        }
    }

    /**
     * Gestion du bouton pour lancer une recherche sur les sites à aspirer
     */
    public void isSourcesValid(){
        if ((isSeloger.isSelected() || isOuestFrance.isSelected()) && isStepOne) {
            sourceBtnValidate.setDisable(false);
        } else {
            sourceBtnValidate.setDisable(true);
        }
    }
}
