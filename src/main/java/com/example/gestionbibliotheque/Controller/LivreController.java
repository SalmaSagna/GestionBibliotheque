package com.example.gestionbibliotheque.Controller;

import com.example.gestionbibliotheque.DAO.EmpruntDAO;
import com.example.gestionbibliotheque.DAO.EtudiantDAO;
import com.example.gestionbibliotheque.DAO.LivreDAO;
import com.example.gestionbibliotheque.Model.Emprunt;
import com.example.gestionbibliotheque.Model.Etudiant;
import com.example.gestionbibliotheque.Model.Livre;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class LivreController {
    LivreDAO dbLivre = new LivreDAO();

    EtudiantDAO dbEtudiant = new EtudiantDAO();

    EmpruntDAO dbEmprunt = new EmpruntDAO();

    @FXML
    private TableView<Livre> tableLivre;

    @FXML
    private TableColumn<Livre,Integer> colId;

    @FXML
    private TableColumn<Livre,String> colISBN;

    @FXML
    private TableColumn<Livre,String> colTitre;

    @FXML
    private TableColumn<Livre,String> colAuteur;

    @FXML
    private TextField txtISBN;

    @FXML
    private TextField txtTitre;

    @FXML
    private TextField txtAuteur;

    @FXML
    private StackPane stackPane;

    private Livre selectedBook;

    private ObservableList<Livre> livres;

    private Etudiant selectedStudent;

    @FXML
    private ComboBox<String> comboMatricule;

    @FXML
    private TextField txtPrenom;

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtEmail;

    @FXML
    private VBox vboxStudent;

    @FXML
    ObservableList<String> matricule;

    @FXML
    private DatePicker dateRetour;

    @FXML
    private TextField txtISBNEmprunt;

    @FXML
    private RadioButton rbDisponibilite;

    @FXML
    private RadioButton rbNonDisponibilite;

    @FXML
    private void initialize(){
        if(tableLivre!=null){
            livres = FXCollections.observableList(dbLivre.getAllBooks());
            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colISBN.setCellValueFactory(new PropertyValueFactory<>("isbn"));
            colTitre.setCellValueFactory(new PropertyValueFactory<>("titre"));
            colAuteur.setCellValueFactory(new PropertyValueFactory<>("auteur"));
            tableLivre.setItems(livres);

            matricule=FXCollections.observableList(dbEtudiant.getAllMatricule());
            comboMatricule.setItems(matricule);
        }
    }

    @FXML
    private void addBook(){
        if (!txtISBN.getText().isBlank() && !txtTitre.getText().isBlank() && !txtAuteur.getText().isBlank()){
            Livre l = new Livre();
            l.setIsbn(txtISBN.getText());
            l.setAuteur(txtAuteur.getText());
            l.setTitre(txtTitre.getText());
            dbLivre.addBook(l);
            refreshTable(0);
            clearInput();
        }
        else {
            String titre = "Attention";
            String message = "Remplir tout les champs d'abord d'abord";
            Alert.AlertType type = Alert.AlertType.WARNING;
            showAlert(titre,message,type);
        }
    }

    @FXML
    private Livre getselectedBook(){
        selectedBook = tableLivre.getSelectionModel().getSelectedItem();
        if (selectedBook!=null) {
            txtAuteur.setText(selectedBook.getAuteur());
            txtTitre.setText(selectedBook.getTitre());
            txtISBN.setText(selectedBook.getIsbn());
            txtISBNEmprunt.setText(selectedBook.getIsbn());
        }
        return selectedBook;
    }

    @FXML
    private void updateBook(){
        if (selectedBook!=null) {
            if (!txtISBN.getText().isBlank() && !txtTitre.getText().isBlank() && !txtAuteur.getText().isBlank()) {
                selectedBook.setTitre(txtTitre.getText());
                selectedBook.setIsbn(txtISBN.getText());
                selectedBook.setAuteur(txtAuteur.getText());
                dbLivre.updateBook(selectedBook);
                refreshTable(0);
                clearInput();
            }
            else {
                String titre = "Attention";
                String message = "Remplir tout les champs d'abord d'abord";
                Alert.AlertType type = Alert.AlertType.WARNING;
                showAlert(titre,message,type);
            }
        } else {
            String titre = "Attention";
            String message = "Selectionner le livre d'abord";
            Alert.AlertType type = Alert.AlertType.WARNING;
            showAlert(titre,message,type);
        }
    }

    private void refreshTable(int var){
        livres.clear();
        livres.addAll(var==0 ? dbLivre.getAllBooks() : (var==1 ? dbLivre.getDisponibleBooks() : dbLivre.getNonDisponibleBooks()));
        tableLivre.setItems(livres);
    }

    private void clearInput(){
        txtISBN.clear();
        txtTitre.clear();
        txtAuteur.clear();
    }

    @FXML
    private void inputStudent(){
        selectedStudent= dbEtudiant.getStudentByMatricule(comboMatricule.getValue());
        txtNom.setText(selectedStudent.getNom());
        txtPrenom.setText(selectedStudent.getPrenom());
        txtEmail.setText(selectedStudent.getEmail());
    }

    @FXML
    private void displayStudent(){
        if (selectedBook!=null) {
            if(dbEmprunt.getEmpruntsByBook(selectedBook.getId()).isEmpty()) {
                vboxStudent.setVisible(true);
            }
            else {
                String titre = "Attention";
                String message = "Le livre a deja été emprunter";
                Alert.AlertType type = Alert.AlertType.WARNING;
                showAlert(titre,message,type);
            }
        }
        else {
            String titre = "Attention";
            String message = "Selectionner le livre d'abord";
            Alert.AlertType type = Alert.AlertType.WARNING;
            showAlert(titre,message,type);
        }
    }

    @FXML
    private void confirmEmprunt(){
        if (selectedStudent!=null && dateRetour.getValue()!=null) {
            if (dateRetour.getValue().isAfter(LocalDate.now())) {
                if (dbEmprunt.getEmpruntsByStudent(selectedStudent.getId()).isEmpty()) {
                    Emprunt emprunt = new Emprunt();
                    emprunt.setLivre(selectedBook);
                    emprunt.setEtudiant(selectedStudent);
                    emprunt.setDateRetour(dateRetour.getValue());
                    emprunt.setDateEmprunt(LocalDate.now());
                    dbEmprunt.addEmprunt(emprunt);
                    vboxStudent.setVisible(false);
                    String nomFichier = "Fiche_Emprunt_" + emprunt.getEtudiant().getMatricule() + ".pdf";
                    dbEmprunt.genererPDFEmprunt(emprunt,nomFichier);
                    dbEmprunt.envoyerEmail(emprunt,nomFichier);
                    showAlert("Succès", "L'emprunt a été enregistré", Alert.AlertType.INFORMATION);
                }
                else {
                    String titre = "Attention";
                    String message = "Cet etudiant a deja emprunté un livre";
                    Alert.AlertType type = Alert.AlertType.WARNING;
                    showAlert(titre,message,type);
                }
            }
            else {
                String titre = "Attention";
                String message = "La date de retour doit etre apres la date actuelle";
                Alert.AlertType type = Alert.AlertType.WARNING;
                showAlert(titre,message,type);
            }
        }
        else {
            String titre = "Attention";
            String message = "Remplir tout les champs d'abord d'abord";
            Alert.AlertType type = Alert.AlertType.WARNING;
            showAlert(titre,message,type);
        }
    }

    private void showAlert(String titre, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null); // On enlève l'entête
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void showLivreDisponibilite(){
        if (rbDisponibilite.isSelected()) {
            rbNonDisponibilite.setSelected(false);
            refreshTable(1);
        }else if (rbNonDisponibilite.isSelected()) {
            rbDisponibilite.setSelected(false);
            refreshTable(2);
        }else refreshTable(0);
    }
}
