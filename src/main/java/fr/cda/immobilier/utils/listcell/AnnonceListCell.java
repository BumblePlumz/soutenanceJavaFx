package fr.cda.immobilier.utils.listcell;

import fr.cda.immobilier.model.entity.Annonce;
import fr.cda.immobilier.utils.tools.LoggerTools;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Gestion des affichages par cellules des annonces
 * Utiliser pour formatter l'affichage d'un listView
 */
public class AnnonceListCell extends ListCell<Annonce> {
    @Override
    protected void updateItem(Annonce annonce, boolean empty){
        try {
            super.updateItem(annonce, empty);

            if (!empty || annonce != null){
                ImageView imageView = new ImageView();
                imageView.setFitWidth(100);
                imageView.setPreserveRatio(true);
                imageView.setImage(new Image(annonce.getImgUrl()));

                VBox detailsBox = new VBox();
                detailsBox.setSpacing(5);
                Label titleLabel = new Label(annonce.getTitle());
                Label hrefLabel = new Label("Lien :"+annonce.getHref());
                Label infosLabel = new Label(annonce.getRoom()+" pièce(s) | "+annonce.getBedroom()+" chambre(s)");
                Label priceLabel = new Label("Prix : " + annonce.getPrice());
                Label sizeLabel = new Label("Surface : "+annonce.getSize()+" m²");
                Label descriptionLabel = new Label("Description : \n"+annonce.getDescription());

                // Ajout des labels à la VBox
                detailsBox.getChildren().addAll(titleLabel, hrefLabel, infosLabel, priceLabel, sizeLabel, descriptionLabel);

                // Création d'une HBox pour aligner l'image et les détails
                HBox hbox = new HBox(10); // Espacement entre l'image et les détails
                hbox.getChildren().addAll(imageView, detailsBox);

                // Définir la cellule avec la HBox contenant l'image et les détails
                setGraphic(hbox);
            }else{
                setText(null);
                setGraphic(null);
            }
        }catch (NullPointerException e){
            LoggerTools.logInfo("Class AnnonceListCell : erreur dans updateItem()", e.getCause());
        }
    }
}
