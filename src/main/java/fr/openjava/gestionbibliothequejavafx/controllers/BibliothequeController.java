package fr.openjava.gestionbibliothequejavafx.controllers;

import fr.openjava.gestionbibliothequejavafx.models.generated.ObjectFactory;
import fr.openjava.gestionbibliothequejavafx.utils.Utilities;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import fr.openjava.gestionbibliothequejavafx.models.generated.Bibliotheque;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.xml.bind.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.stage.FileChooser;
import java.time.LocalDate;

public class BibliothequeController {

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    private TableView<Bibliotheque.Livre> tableView = null;

    @FXML
    private TableColumn<Bibliotheque.Livre, String> titreColumn;

    @FXML
    private TableColumn<Bibliotheque.Livre, String> auteurColumn;

    @FXML
    private TableColumn<Bibliotheque.Livre, String> presentationColumn;

    @FXML
    private TableColumn<Bibliotheque.Livre, Integer> parutionColumn;

    @FXML
    private TableColumn<Bibliotheque.Livre, Integer> colonneColumn;

    @FXML
    private TableColumn<Bibliotheque.Livre, Integer> rangeeColumn;

    private ObservableList<Bibliotheque.Livre> livres = FXCollections.observableArrayList();
    //private Bibliotheque.Livre currentLivre = new Bibliotheque.Livre();
    private List<Bibliotheque.Livre> allCurrentLivre = new ArrayList<Bibliotheque.Livre>();
    @FXML
    private TextArea titreTextArea;

    @FXML
    private TextArea auteurTextArea;

    @FXML
    private TextArea presentationTextArea;

    @FXML
    private TextArea parutionTextArea;

    @FXML
    private TextArea colonneTextArea;

    @FXML
    private TextArea rangeeTextArea;

    public BibliothequeController(){
        System.out.println("################## Lancement");
    }

    /**
     * Méthode pour initialiser le contenu des colones du tableau
     */
    public void initialize() {

        titreColumn.setCellValueFactory(new PropertyValueFactory<>("titre"));
        auteurColumn.setCellValueFactory(cellData -> {
            /**
             * Récupérer l'objet Livre correspondant à la ligne actuelle
             * Récupérer l'objet Auteur du livre
             * Retourner une SimpleStringProperty contenant le nom complet de l'auteur
              */
            Bibliotheque.Livre livre = cellData.getValue();
            Bibliotheque.Livre.Auteur auteur = livre.getAuteur();
            return new SimpleStringProperty(auteur.getNom() + " " + auteur.getPrenom());
        });
        presentationColumn.setCellValueFactory(new PropertyValueFactory<>("presentation"));
        parutionColumn.setCellValueFactory(new PropertyValueFactory<>("parution"));
        colonneColumn.setCellValueFactory(new PropertyValueFactory<>("colonne"));
        rangeeColumn.setCellValueFactory(new PropertyValueFactory<>("rangee"));
        editLivreListener();
        chargerDonneesDuXML();
    }

    /**
     * Méthode pour récupérer les données du fichier xml
     */
    private void chargerDonneesDuXML() {
        try {

            JAXBContext jaxbContext = JAXBContext.newInstance(Bibliotheque.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            //unmarshaller.setValidating(true);
            Bibliotheque bibliotheque = (Bibliotheque) unmarshaller.unmarshal(new File(Utilities.XML_FILE_PATH));
            for (Bibliotheque.Livre livre : bibliotheque.getLivre()) {
                String data = livre.getAuteur().getNom() + " " + livre.getAuteur().getPrenom();
                Bibliotheque.Livre.Auteur auteur =  new Bibliotheque.Livre.Auteur();
                //auteur.setNom(livre.getAuteur().getNom());
                //auteur.setPrenom(livre.getAuteur().getPrenom());
                //livre.setAuteur(auteur);
                System.out.println("########################## nom " + livre.getAuteur().getNom());

                System.out.println("########################## prenom " + livre.getAuteur().getPrenom());
            }

            livres.addAll(bibliotheque.getLivre());
            tableView.setItems(livres);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode pour récupérer les données du formulaire et
     * créer un nouveau livre
     */
    public void saveLivre() {

        /*
        // Récupérer les valeurs des champs
        String titre = titreTextArea.getText();
        String[] auteur = auteurTextArea.getText().split(" ");
        if (auteur.length == 1){
            Utilities.showAlert("Erreur de validation", "Vous devez saisir le nom suivi du prénom de l'auteur");
        }
        String presentation = presentationTextArea.getText();

        int parution=0, colonne=0, rangee=0;
        try {
            parution = Integer.parseInt(parutionTextArea.getText());
        } catch (NumberFormatException e) {
            Utilities.showAlert("Erreur de validation", "La parution doit être une année valide !");
        }

        try {
            colonne = Integer.parseInt(colonneTextArea.getText());
        } catch (NumberFormatException e) {
            Utilities.showAlert("Erreur de validation", "La colone doit être un nombre valide !");
        }

        try {
            rangee = Integer.parseInt(rangeeTextArea.getText());
        } catch (NumberFormatException e) {
            Utilities.showAlert("Erreur de validation", "La colone doit être un nombre valide !");
        }

        Bibliotheque.Livre livre = new Bibliotheque.Livre();
        livre.setTitre(titre);

        Bibliotheque.Livre.Auteur auteurObj = new Bibliotheque.Livre.Auteur();
        auteurObj.setNom(auteur[0]);
        auteurObj.setPrenom(auteur[1]);
        livre.setAuteur(auteurObj);

        livre.setPresentation(presentation);
        livre.setParution(parution);
        livre.setColonne((short) colonne);
        livre.setRangee((short) rangee);
*/
        /**
         * Enregistrer le livre courant au fichier XML
          */
        addLivreToXML();
    }

    /**
     * Méthode pour enregistrer les livres dans un autres fichier
     */
    public void saveInOtherLocation() throws JAXBException {

        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer L'ensemble des livres de la Bibliothèque sous");

        // Ajouter une extension de fichier par défaut (facultatif)
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Fichiers texte (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Afficher la boîte de dialogue et obtenir l'emplacement du fichier
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            createXMLFileWithLivre(file.getAbsolutePath());
            System.out.println("Fichier enregistré à : " + file.getAbsolutePath());
        }

    }

    /**
     * Méthode pour ajouter un livre dans le tableau sans l'enregistrer dans le fichier XML
     */
    public void addLivreToTabview(){

        Bibliotheque.Livre currentLivre = new Bibliotheque.Livre();
        LocalDate currentDate = LocalDate.now();

        if (titreTextArea.getText().isEmpty() || auteurTextArea.getText().isEmpty() || presentationTextArea.getText().isEmpty() || rangeeTextArea.getText().isEmpty() || colonneTextArea.getText().isEmpty() || parutionTextArea.getText().isEmpty() ){
            Utilities.showAlert("Erreur de validation", "Tous les champs sont obligatoires !");
        }else {
            String titre = titreTextArea.getText();
            String[] auteur = auteurTextArea.getText().split(" ");
            if (auteur.length == 1){
                Utilities.showAlert("Erreur de validation", "Vous devez saisir le nom suivi du prénom de l'auteur");
            }
            String presentation = presentationTextArea.getText();

            try {
                int parution = Integer.parseInt(parutionTextArea.getText());
                if (!(parution <= currentDate.getYear())){
                    Utilities.showAlert("Erreur de validation", "L'année de parution ne peut pas être supérieur à l'année du jour");
                    return;
                }
                currentLivre.setParution(parution);
            } catch (NumberFormatException e) {
                Utilities.showAlert("Erreur de validation", "L'année de parution doit être une année valide !");
                return;
            }

            try {
                int colonne = Integer.parseInt(colonneTextArea.getText());
                if (!(colonne <= 7 && colonne >= 0)){
                    Utilities.showAlert("Erreur de validation", "La colonne doit avoir une valeur minimum 0 et valeur maximum 7");
                    return;
                }
                currentLivre.setColonne((short) colonne);
            } catch (NumberFormatException e) {
                Utilities.showAlert("Erreur de validation", "La colonne doit être un nombre valide !");
                return;
            }

            try {
                int rangee = Integer.parseInt(rangeeTextArea.getText());
                if (!(rangee <= 5 && rangee >= 1)){
                    Utilities.showAlert("Erreur de validation", "La rangée doit avoir une valeur minimum 1 et valeur maximum 5");
                    return;
                }
                currentLivre.setRangee((short) rangee);
            } catch (NumberFormatException e) {
                Utilities.showAlert("Erreur de validation", "La rangée doit être un nombre valide !");
                return;
            }


            /**
             * Créer un nouvel objet Livre et l'ajouter dans le tableau
             */
            currentLivre.setTitre(titre);
            Bibliotheque.Livre.Auteur currentAuteur = new Bibliotheque.Livre.Auteur();
            currentAuteur.setNom(auteur[0]);
            currentAuteur.setPrenom(auteur[1]);
            currentLivre.setAuteur(currentAuteur);
            currentLivre.setPresentation(presentation);

            allCurrentLivre.add(currentLivre);
            livres.add(currentLivre);
            tableView.setItems(livres);
        }

    }

    /**
     * Méthode pour ajouter un livre dans le fichier XML
     */
    private void addLivreToXML() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Bibliotheque.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Charger le fichier XML existant
            File file = new File(Utilities.XML_FILE_PATH);
            Bibliotheque bibliotheque;
            if (file.exists()) {
                bibliotheque = (Bibliotheque) jaxbContext.createUnmarshaller().unmarshal(file);
            } else {
                bibliotheque = new ObjectFactory().createBibliotheque();
            }
            // Ajouter le livre à la liste des livres
            //bibliotheque.getLivre().add(livre);
            for (Bibliotheque.Livre thelivre : allCurrentLivre){
                bibliotheque.getLivre().add(thelivre);
            }
            // Enregistrer les modifications dans le fichier XML
            marshaller.marshal(bibliotheque, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    /**
     * Recharger les données du fichier XML pour être pris en compte
     * dans la vue de listing des livres
     */
    public void reloadDataToXML(){
        livres.clear();
        titreColumn.setCellValueFactory(new PropertyValueFactory<>("titre"));
         auteurColumn.setCellValueFactory(cellData -> {
            Bibliotheque.Livre livre = cellData.getValue();
            Bibliotheque.Livre.Auteur auteur = livre.getAuteur();
            return new SimpleStringProperty(auteur.getNom() + " " + auteur.getPrenom());
        });
        presentationColumn.setCellValueFactory(new PropertyValueFactory<>("presentation"));
        parutionColumn.setCellValueFactory(new PropertyValueFactory<>("parution"));
        colonneColumn.setCellValueFactory(new PropertyValueFactory<>("colonne"));
        rangeeColumn.setCellValueFactory(new PropertyValueFactory<>("rangee"));

        chargerDonneesDuXML();
    }

    /**
     * Méthode pour recuperer les valeurs des champs d'un livre lors de sa selection
     * et l'afficher dans le formulaire
     */
    public void editLivreListener() {
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Récupérez les valeurs sélectionnées et affichez-les dans les champs de texte
                titreTextArea.setText(newSelection.getTitre());
                Bibliotheque.Livre.Auteur auteur = newSelection.getAuteur();
                auteurTextArea.setText(auteur.getNom().concat(" ").concat(auteur.getPrenom()));
                presentationTextArea.setText(newSelection.getPresentation());
                parutionTextArea.setText(Integer.toString(newSelection.getParution()));
                colonneTextArea.setText(Short.toString(newSelection.getColonne()));
                rangeeTextArea.setText(Short.toString(newSelection.getRangee()));
            }
        });
    }

    /**
     * Méthode pour créer un nouveau fichier XML et enregistrer les livres
     */
    private void createXMLFileWithLivre(String pathOfFile) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Bibliotheque.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Charger le fichier XML existant
            File file = new File(Utilities.XML_FILE_PATH);
            Bibliotheque bibliotheque;
            if (file.exists()) {
                bibliotheque = (Bibliotheque) jaxbContext.createUnmarshaller().unmarshal(file);
            } else {
                bibliotheque = new ObjectFactory().createBibliotheque();
            }
            // Ajouter le livre à la liste des livres
            for (Bibliotheque.Livre livre : allCurrentLivre){
                bibliotheque.getLivre().add(livre);
            }

            File currentFile = new File(pathOfFile);
            // Enregistrer les modifications dans le fichier XML
            marshaller.marshal(bibliotheque, currentFile);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}